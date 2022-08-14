package bit.minisys.minicc.semantic;

import bit.minisys.minicc.parser.ast.*;

public class VisitSymbolTable implements ASTVisitor {

    public SymbolTable globalSymbolTable;
    public SymbolTable localSymbolTable;
    public SymbolTable functionTable;
    public ErrorInfo errorInfo;
    public int iterationDepth;

    public VisitSymbolTable(SymbolTable table, ErrorInfo errorInfo) {
        this.globalSymbolTable = table;
        this.localSymbolTable = table;
        this.functionTable = new SymbolTable();
        this.errorInfo = errorInfo;
        this.iterationDepth = 0;
    }

    // functionList -> functionDefine functionList | e
    @Override
    public void visit(ASTCompilationUnit program) throws Exception {
        program.scope = this.globalSymbolTable;
        for (ASTNode astNode : program.items) {
            if (astNode instanceof ASTDeclaration) {
                this.visit((ASTDeclaration) astNode);
            } else if (astNode instanceof ASTFunctionDefine) {
                this.visit((ASTFunctionDefine) astNode);
            }
        }
    }

    // declaration -> typeSpecifiers initDeclaratorList ';'
    // initDeclaratorList -> initDeclarator | initDeclarator ',' initDeclaratorList
    // initDeclarator -> declarator | declarator '=' initializer
    // declarator -> identifier postDeclarator
    // postDeclarator -> '[' assignmentExpression ']' postDeclarator | '[' ']' postDeclarator | e
    // initializer -> assignmentExpression | '{' expression '}'
    @Override
    public void visit(ASTDeclaration declaration) throws Exception {
        declaration.scope = this.localSymbolTable;

        String typeSpecifiers = "";
        for (ASTToken typeSpecifier : declaration.specifiers) {
            typeSpecifiers += " " + typeSpecifier.value;
        }

        for (ASTInitList initDeclarator : declaration.initLists) {
            this.visit(initDeclarator);

            String name = initDeclarator.declarator.getName();

            // declarator是否重定义
            if (this.localSymbolTable.findPresent(name)) {
                errorInfo.addES02Declaration(name);
                return;
            }

            if (declaration.scope == this.globalSymbolTable) {
                this.globalSymbolTable.addVariable(name, typeSpecifiers);
            } else {
                this.localSymbolTable.addVariable(name, typeSpecifiers);
            }
        }
    }

    @Override
    public void visit(ASTArrayDeclarator arrayDeclarator) throws Exception {
        arrayDeclarator.scope = this.localSymbolTable;
        this.visit(arrayDeclarator.declarator);
        this.visit(arrayDeclarator.expr);
    }

    @Override
    public void visit(ASTVariableDeclarator variableDeclarator) throws Exception {
        variableDeclarator.scope = this.localSymbolTable;
    }

    @Override
    public void visit(ASTFunctionDeclarator functionDeclarator) throws Exception {
        this.visit(functionDeclarator.declarator);
        for (ASTParamsDeclarator astParamsDeclarator : functionDeclarator.params) {
            this.visit(astParamsDeclarator);
        }
    }

    @Override
    public void visit(ASTParamsDeclarator paramsDeclarator) throws Exception {
        this.visit(paramsDeclarator.declarator);
    }

    @Override
    public void visit(ASTArrayAccess arrayAccess) throws Exception {
        String arrayName = "";
        ASTExpression compoundArrayName = arrayAccess.arrayName;
        ASTExpression index = arrayAccess.elements.get(0);

        while (true) {
            // a[i]中i未定义
            if (index instanceof ASTIdentifier && !localSymbolTable.findAll(((ASTIdentifier) index).value)) {
                errorInfo.addES01Identifier(((ASTIdentifier) index).value);
                return;
            }

            // a[i]中a未定义
            if (!localSymbolTable.findAll(arrayName)) {
                errorInfo.addES01Identifier(arrayName);
                return;
            }

            // a[i][j]...[k]
            if (compoundArrayName instanceof ASTArrayAccess) {
                index = ((ASTArrayAccess) compoundArrayName).elements.get(0);
                compoundArrayName = ((ASTArrayAccess) compoundArrayName).arrayName;
            } else {
                arrayName = ((ASTIdentifier) compoundArrayName).value;
                break;
            }

        }
    }

    @Override
    public void visit(ASTBinaryExpression binaryExpression) throws Exception {
        this.visit(binaryExpression.expr1);
        this.visit(binaryExpression.expr2);
    }

    @Override
    public void visit(ASTBreakStatement breakStat) throws Exception {
        if (iterationDepth <= 0) {
            errorInfo.addES03();
        }
    }

    @Override
    public void visit(ASTContinueStatement continueStatement) throws Exception {
        if (this.iterationDepth <= 0) {
            errorInfo.addES03();
        }
    }

    @Override
    public void visit(ASTCastExpression castExpression) throws Exception {

    }

    @Override
    public void visit(ASTCharConstant charConst) throws Exception {

    }

    // compoundStatement -> '{' blockItemList '}'
    // blockItemList -> declaration blockItemList | statement blockItemList | e
    @Override
    public void visit(ASTCompoundStatement compoundStat) throws Exception {
        // 复合语句形成新的作用域
        compoundStat.scope = this.localSymbolTable;
        this.localSymbolTable = new SymbolTable();
        this.localSymbolTable.father = compoundStat.scope;

        for (ASTNode astNode : compoundStat.blockItems) {

//            System.out.println("compoundStat:");
//            System.out.println(this.localSymbolTable.items.size());
//            for (Map.Entry entry : this.localSymbolTable.items.entrySet()) {
//                System.out.println("key:" + entry.getKey() + "   value:" + entry.getValue());
//            }

            if (astNode instanceof ASTDeclaration) {
                visit((ASTDeclaration) astNode);
            } else if (astNode instanceof ASTStatement) {
                visit((ASTStatement) astNode);
            }
        }

        // 还原作用域
        this.localSymbolTable = compoundStat.scope;
    }

    @Override
    public void visit(ASTConditionExpression conditionExpression) throws Exception {

    }

    // expression -> assignmentExpression ',' expression | assignmentExpression
    // assignmentExpression -> conditionalExpression | unaryExpression assignmentOperator assignmentExpression
    // conditionalExpression -> logicalOrExpression | logicalOrExpression '?' expression ':' conditionalExpression
    // logicalOrExpression -> logicalAndExpression | logicalAndExpression '||' logicalOrExpression
    // logicalAndExpression -> equalityExpression | equalityExpression '&&' logicalAndExpression
    // equalityExpression -> relationalExpression | relationalExpression '==' equalityExpression | relationalExpression '!=' equalityExpression
    // relationalExpression -> additiveExpression | additiveExpression '<' relationalExpression | additiveExpression '>' relationalExpression | additiveExpression '<=' relationalExpression | additiveExpression '>=' relationalExpression
    // additiveExpression -> multiplicativeExpression | multiplicativeExpression '+' additiveExpression | multiplicativeExpression '-' additiveExpression
    // multiplicativeExpression -> castExpression | castExpression '*' multiplicativeExpression |  castExpression '/' multiplicativeExpression | castExpression '%' multiplicativeExpression
    // castExpression -> unaryExpression | '(' typeSpecifiers ')' castExpression
    // unaryExpression -> postfixExpression | unaryOperator castExpression
    // postfixExpression -> primaryExpression postfixExpressionPost
    // postfixExpressionPost -> '[' expression ']' postfixExpressionPost | '(' expression ')' postfixExpressionPost |  '(' ')' postfixExpressionPost | '.' postfixExpressionPost | '->' postfixExpressionPost | '++' postfixExpressionPost | '--' postfixExpressionPost | e
    // primaryExpression -> Identifier | IntegerConstant | FloatingConstant | CharacterConstant | StringLiteral | '(' expression ')'
    @Override
    public void visit(ASTExpression expression) throws Exception {
        if (expression instanceof ASTConditionExpression) {
            this.visit((ASTConditionExpression) expression);
        } else if (expression instanceof ASTBinaryExpression) {
            this.visit((ASTBinaryExpression) expression);
        } else if (expression instanceof ASTCastExpression) {
            this.visit((ASTCastExpression) expression);
        } else if (expression instanceof ASTUnaryExpression) {
            this.visit((ASTUnaryExpression) expression);
        } else if (expression instanceof ASTPostfixExpression) {
            this.visit((ASTPostfixExpression) expression);
        } else if (expression instanceof ASTArrayAccess) {
            this.visit((ASTArrayAccess) expression);
        } else if (expression instanceof ASTMemberAccess) {
            this.visit((ASTMemberAccess) expression);
        } else if (expression instanceof ASTFunctionCall) {
            this.visit((ASTFunctionCall) expression);
        } else if (expression instanceof ASTIdentifier) {
            this.visit((ASTIdentifier) expression);
        } else if (expression instanceof ASTIntegerConstant) {
            this.visit((ASTIntegerConstant) expression);
        } else if (expression instanceof ASTFloatConstant) {
            this.visit((ASTFloatConstant) expression);
        } else if (expression instanceof ASTCharConstant) {
            this.visit((ASTCharConstant) expression);
        } else if (expression instanceof ASTStringConstant) {
            this.visit((ASTStringConstant) expression);
        }
    }

    @Override
    public void visit(ASTExpressionStatement expressionStat) throws Exception {
        for (ASTExpression astExpression : expressionStat.exprs) {
            this.visit(astExpression);
        }
    }

    @Override
    public void visit(ASTFloatConstant floatConst) throws Exception {

    }

    @Override
    public void visit(ASTFunctionCall funcCall) throws Exception {
        String functionName = ((ASTIdentifier) funcCall.funcname).value;

//        System.out.println("functioncall:");
//        System.out.println(functionName);
//        System.out.println(this.localSymbolTable.items.size());
//        for (Map.Entry entry : this.localSymbolTable.items.entrySet()) {
//            System.out.println("key:" + entry.getKey() + "   value:" + entry.getValue());
//        }

        // 函数未定义
        if (!this.functionTable.findAll(functionName)) {
            errorInfo.addES01FunctionCall(functionName);
        }
    }

    @Override
    public void visit(ASTGotoStatement gotoStat) throws Exception {

    }

    @Override
    public void visit(ASTIdentifier identifier) throws Exception {
        String name = identifier.value;
        if (!this.localSymbolTable.findAll(name)) {
            errorInfo.addES01Identifier(name);
        }
    }

    @Override
    public void visit(ASTInitList initList) throws Exception {
        this.visit(initList.declarator);
        for (ASTExpression expr : initList.exprs) {
            this.visit(expr);
        }
    }

    @Override
    public void visit(ASTIntegerConstant intConst) throws Exception {

    }

    @Override
    public void visit(ASTIterationDeclaredStatement iterationDeclaredStat) throws Exception {
        // 形成新的作用域
        iterationDeclaredStat.scope = localSymbolTable;
        localSymbolTable = new SymbolTable();
        localSymbolTable.father = iterationDeclaredStat.scope;
        this.iterationDepth += 1;

        if (iterationDeclaredStat.init != null) {
            this.visit(iterationDeclaredStat.init);
        }

        if (iterationDeclaredStat.cond != null) {
            for (ASTExpression cond : iterationDeclaredStat.cond) {
                this.visit(cond);
            }
        }

        if (iterationDeclaredStat.step != null) {
            for (ASTExpression step : iterationDeclaredStat.step) {
                this.visit(step);
            }
        }

        this.visit(iterationDeclaredStat.stat);

        this.iterationDepth -= 1;
        this.localSymbolTable = iterationDeclaredStat.scope;
    }

    @Override
    public void visit(ASTIterationStatement iterationStat) throws Exception {
        // 形成新的作用域
        iterationStat.scope = this.localSymbolTable;
        localSymbolTable = new SymbolTable();
        localSymbolTable.father = iterationStat.scope;
        this.iterationDepth += 1;

        if (iterationStat.init != null) {
            for (ASTExpression init : iterationStat.init) {
                this.visit(init);
            }
        }

        if (iterationStat.cond != null) {
            for (ASTExpression cond : iterationStat.cond) {
                this.visit(cond);
            }
        }

        if (iterationStat.step != null) {
            for (ASTExpression step : iterationStat.step) {
                this.visit(step);
            }
        }

        this.visit(iterationStat.stat);

        this.iterationDepth -= 1;
        this.localSymbolTable = iterationStat.scope;
    }

    @Override
    public void visit(ASTLabeledStatement labeledStat) throws Exception {
        this.visit(labeledStat.stat);
    }

    @Override
    public void visit(ASTMemberAccess memberAccess) throws Exception {
        ASTExpression compoundMaster = memberAccess.master;
        while (compoundMaster instanceof ASTMemberAccess) {
            compoundMaster = ((ASTMemberAccess) compoundMaster).master;
        }
        String master = ((ASTIdentifier) compoundMaster).value;

        // memberAccess中master未定义
        if (!this.localSymbolTable.findAll(master)) {
            errorInfo.addES01Identifier(master);
        }

    }

    @Override
    public void visit(ASTPostfixExpression postfixExpression) throws Exception {
        this.visit(postfixExpression.expr);
    }

    @Override
    public void visit(ASTReturnStatement returnStat) throws Exception {
        returnStat.scope = this.localSymbolTable;
        for (ASTExpression expr : returnStat.expr) {
            this.visit(expr);
        }
    }

    @Override
    public void visit(ASTSelectionStatement selectionStat) throws Exception {
        for (ASTExpression expr : selectionStat.cond) {
            this.visit(expr);
        }
        this.visit(selectionStat.then);
        this.visit(selectionStat.otherwise);
    }

    @Override
    public void visit(ASTStringConstant stringConst) throws Exception {

    }

    @Override
    public void visit(ASTTypename typename) throws Exception {

    }

    @Override
    public void visit(ASTUnaryExpression unaryExpression) throws Exception {
        this.visit(unaryExpression.expr);
    }

    @Override
    public void visit(ASTUnaryTypename unaryTypename) throws Exception {

    }

    // functionDefine -> typeSpecifiers declarator '(' arguments ')' compoundStatement
    @Override
    public void visit(ASTFunctionDefine functionDefine) throws Exception {
        String typeSpecifiers = "";
        for (ASTToken typeSpecifier : functionDefine.specifiers) {
            typeSpecifiers += " " + typeSpecifier.value;
        }

        String funcname = functionDefine.declarator.getName();
        if (this.functionTable.findAll(funcname)) {
            errorInfo.addES02FunctionDefine(funcname);
            return;
        }

        functionDefine.scope = this.localSymbolTable;
        this.localSymbolTable = new SymbolTable();

        ASTFunctionDeclarator astFunctionDeclarator = (ASTFunctionDeclarator) functionDefine.declarator;
        for (ASTParamsDeclarator param : astFunctionDeclarator.params) {
            String paramTypeSpecifiers = "";
            for (ASTToken typeSpecifier : param.specfiers) {
                paramTypeSpecifiers += " " + typeSpecifier.value;
            }
            String paramName = param.declarator.getName();
            this.localSymbolTable.addVariable(paramName, paramTypeSpecifiers);
        }

        this.functionTable.addVariable(funcname, typeSpecifiers);

        this.visit(functionDefine.declarator);
        this.visit(functionDefine.body);

        this.localSymbolTable = this.globalSymbolTable;
    }

    @Override
    public void visit(ASTDeclarator declarator) throws Exception {
        if (declarator instanceof ASTVariableDeclarator) {
            visit((ASTVariableDeclarator) declarator);
        } else if (declarator instanceof ASTArrayDeclarator) {
            visit((ASTArrayDeclarator) declarator);
        } else if (declarator instanceof ASTFunctionDeclarator) {
            visit((ASTFunctionDeclarator) declarator);
        }
    }

    // statement -> breakStatement | continueStatement | gotoStatement |
    //              returnStatement | compoundStatement | selectionStatement |
    //              iterationStatement | iterationDeclaredStatement | expressionStatement
    @Override
    public void visit(ASTStatement statement) throws Exception {
        if (statement instanceof ASTBreakStatement) {
            this.visit((ASTBreakStatement) statement);
        } else if (statement instanceof ASTContinueStatement) {
            this.visit((ASTContinueStatement) statement);
        } else if (statement instanceof ASTGotoStatement) {
            this.visit((ASTGotoStatement) statement);
        } else if (statement instanceof ASTReturnStatement) {
            this.visit((ASTReturnStatement) statement);
        } else if (statement instanceof ASTCompoundStatement) {
            this.visit((ASTCompoundStatement) statement);
        } else if (statement instanceof ASTSelectionStatement) {
            this.visit((ASTSelectionStatement) statement);
        } else if (statement instanceof ASTIterationStatement) {
            this.visit((ASTIterationStatement) statement);
        } else if (statement instanceof ASTIterationDeclaredStatement) {
            this.visit((ASTIterationDeclaredStatement) statement);
        } else if (statement instanceof ASTExpressionStatement) {
            this.visit((ASTExpressionStatement) statement);
        } else if (statement instanceof ASTLabeledStatement) {
            this.visit((ASTLabeledStatement) statement);
        }
    }

    @Override
    public void visit(ASTToken token) throws Exception {

    }
}
