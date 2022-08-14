package bit.minisys.minicc.icgen;

import bit.minisys.minicc.parser.ast.*;
import bit.minisys.minicc.semantic.SymbolTable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// 一个简单样例，只实现了加法
public class ExampleICBuilder implements ASTVisitor {

    private Map<ASTNode, ASTNode> map;                // 使用map存储子节点的返回值，key对应子节点，value对应返回值，value目前类别包括ASTIdentifier,ASTIntegerConstant,TemportaryValue...
    private List<Quat> quats;                        // 生成的四元式列表
    private int tmpId;                            // 临时变量编号

    // 符号表
    public SymbolTable globalSymbolTable;
    public SymbolTable localSymbolTable;
    public SymbolTable functionTable;

    Map<String, ASTNode> labelTable;

    // 临时存放arrayAccess
    Quat arr;
    public boolean leftArr;

    // 循环控制
    public int iterationId;
    public int jmpLabelId;

    public ExampleICBuilder() {
        this.map = new HashMap<ASTNode, ASTNode>();
        this.quats = new LinkedList<Quat>();
        this.tmpId = 0;
        this.iterationId = 0;
        this.jmpLabelId = 0;
        this.leftArr = false;

        this.globalSymbolTable = new SymbolTable();
        this.localSymbolTable = new SymbolTable();
        this.functionTable = new SymbolTable();

        this.labelTable = new HashMap<String, ASTNode>();
    }

    public List<Quat> getQuats() {
        return quats;
    }

    // functionList -> functionDefine functionList | e
    @Override
    public void visit(ASTCompilationUnit program) throws Exception {
        for (ASTNode node : program.items) {
            if (node instanceof ASTFunctionDefine) {
                this.visit((ASTFunctionDefine) node);
            } else if (node instanceof ASTDeclaration) {
                this.visit((ASTDeclaration) node);
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

        String typeSpecifiers = declaration.specifiers.get(0).value;

        for (ASTInitList initDeclarator : declaration.initLists) {

            String name = initDeclarator.declarator.getName();
            ASTDeclarator declarator = initDeclarator.declarator;

            if (declarator instanceof ASTVariableDeclarator) {
                if (declaration.scope == this.globalSymbolTable) {
                    this.globalSymbolTable.addVariable(name, typeSpecifiers);
                } else if (declaration.scope == this.localSymbolTable) {
                    this.localSymbolTable.addVariable(name, typeSpecifiers);
                }
                Quat quat = new Quat("var", declarator, declaration.specifiers.get(0), null);
                quats.add(quat);

                // 有初值，需再加一个赋初值的四元式
                if (!initDeclarator.exprs.isEmpty()) {
                    String op = "=";
                    ASTNode opnd1 = null;
                    ASTNode opnd2 = null;

                    ASTExpression expr = initDeclarator.exprs.get(0);

                    // 用其他变量或常量赋值
                    if (expr instanceof ASTIdentifier || expr instanceof ASTIntegerConstant ||
                            expr instanceof ASTFloatConstant || expr instanceof ASTCharConstant ||
                            expr instanceof ASTStringConstant) {
                        opnd1 = expr;
                    }
                    // 双目运算
                    else if (expr instanceof ASTBinaryExpression) {
                        ASTBinaryExpression be = (ASTBinaryExpression) expr;
                        op = be.op.value;
                        this.visit(be.expr1);
                        this.visit(be.expr2);
                        opnd1 = map.get(be.expr1);
                        opnd2 = map.get(be.expr2);
                    }
                    // 单目运算
                    else if (expr instanceof ASTPostfixExpression || expr instanceof ASTUnaryExpression ||
                            expr instanceof ASTFunctionCall || expr instanceof ASTArrayAccess) {
                        this.visit(expr);
                        opnd1 = map.get(expr);
                    }
                    Quat quat1 = new Quat(op, declarator, opnd1, opnd2);
                    quats.add(quat1);
                    map.put(initDeclarator, declarator);
                }
            } else if (declarator instanceof ASTArrayDeclarator) {
                ASTDeclarator arrayDeclarator = ((ASTArrayDeclarator) declarator).declarator;
                ASTExpression expr = ((ASTArrayDeclarator) declarator).expr;

                LinkedList<Integer> arrayLimit = new LinkedList<Integer>();

                // 取出数组各维大小，并加入变量类型中，新变量类型实例 int[10][20]
                int limit = ((ASTIntegerConstant) expr).value;
                arrayLimit.addFirst(limit);
                while (arrayDeclarator instanceof ASTArrayDeclarator) {
                    expr = ((ASTArrayDeclarator) arrayDeclarator).expr;
                    arrayDeclarator = ((ASTArrayDeclarator) arrayDeclarator).declarator;

                    limit = ((ASTIntegerConstant) expr).value;
                    arrayLimit.addFirst(limit);
                }

                // 生成复合数组变量类型
                for (int dim : arrayLimit) {
                    typeSpecifiers += "[" + dim + "]";
                }
                DescriptionLabel compoundTypeLabel = new DescriptionLabel(typeSpecifiers);

                if (declaration.scope == this.globalSymbolTable) {
                    this.globalSymbolTable.addVariable(name, typeSpecifiers, arrayLimit);
                } else {
                    this.localSymbolTable.addVariable(name, typeSpecifiers, arrayLimit);
                }

                Quat quat = new Quat("arr", arrayDeclarator, compoundTypeLabel, null);
                quats.add(quat);
            }
        }
    }

    @Override
    public void visit(ASTArrayDeclarator arrayDeclarator) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTVariableDeclarator variableDeclarator) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTFunctionDeclarator functionDeclarator) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTParamsDeclarator paramsDeclarator) throws Exception {
        // TODO Auto-generated method stub

    }

    // a[idx(1)]...[idx(n)]要先计算相对位置再取值
    // ("*", idx(n-1), size(n), temp1), ("+", temp1, idx(n), temp1)
    @Override
    public void visit(ASTArrayAccess arrayAccess) throws Exception {
        // TODO Auto-generated method stub
        ASTExpression compoundArrayName = arrayAccess.arrayName;
        ASTExpression currentIndex = arrayAccess.elements.get(0);

        // 访问数组下标
        LinkedList<ASTNode> index = new LinkedList<>();

        this.visit(currentIndex);
        ASTNode rest = map.get(currentIndex);
        index.addFirst(rest);

        while (compoundArrayName instanceof ASTArrayAccess) {
            currentIndex = ((ASTArrayAccess) compoundArrayName).elements.get(0);
            compoundArrayName = ((ASTArrayAccess) compoundArrayName).arrayName;

            this.visit(currentIndex);
            rest = map.get(currentIndex);
            index.addFirst(rest);
        }
        String arrayName = ((ASTIdentifier) compoundArrayName).value;

        // 数组实际大小
        LinkedList<Integer> size = null;
        if (this.localSymbolTable.findAll(arrayName)) {
            size = this.localSymbolTable.getArrayLimit(arrayName);
        } else {
            size = this.globalSymbolTable.getArrayLimit(arrayName);
        }

        for (int i = 0; i < size.size(); i++) {
            System.out.println(size.get(i));
        }

        // 记录到各维的数组大小
        int num = 1;
        LinkedList<Integer> record = new LinkedList<>();
        for (int i = size.size() - 1; i > 0; i--) {
            num *= size.get(i);
            record.addFirst(num);
        }

        ASTNode t1 = new TemporaryValue(++tmpId);
        ASTNode t2 = new TemporaryValue(++tmpId);

        if (this.globalSymbolTable == this.localSymbolTable) {
            this.globalSymbolTable.addVariable(((TemporaryValue) t1).name(), "int");
            this.globalSymbolTable.addVariable(((TemporaryValue) t2).name(), "int");
        } else {
            this.localSymbolTable.addVariable(((TemporaryValue) t1).name(), "int");
            this.localSymbolTable.addVariable(((TemporaryValue) t2).name(), "int");
        }

        // 生成数组嵌套计算大小的四元式
        DescriptionLabel defaultValue = new DescriptionLabel("0");
        Quat quat = new Quat("=", t2, defaultValue, null);
        quats.add(quat);

        for (int i = 0; i < size.size() - 1; i++) {
            ASTIntegerConstant temp = new ASTIntegerConstant((Integer) record.get(i), -1);
            Quat quat1 = new Quat("*", t1, (ASTNode) index.get(i), temp);
            quats.add(quat1);
            Quat quat2 = new Quat("+", t2, t1, t2);
            quats.add(quat2);
        }

        Quat quat3 = new Quat("+", t2, t2, (ASTNode) index.get(index.size() - 1));
        quats.add(quat3);

        ASTNode t3 = new TemporaryValue(++tmpId);
        if (this.globalSymbolTable == this.localSymbolTable) {
            this.globalSymbolTable.addVariable(((TemporaryValue) t3).name(), "int");
        } else {
            this.localSymbolTable.addVariable(((TemporaryValue) t3).name(), "int");
        }

        Quat quat4 = new Quat("=[]", t3, t2, compoundArrayName);
        quats.add(quat4);
        map.put(arrayAccess, t3);

        // 临时存放数组信息
        if (leftArr) {
            this.arr = quat4;
        }
    }

    @Override
    public void visit(ASTBinaryExpression binaryExpression) throws Exception {
        String op = binaryExpression.op.value;
        ASTNode res = null;
        ASTNode opnd1 = null;
        ASTNode opnd2 = null;

        if (op.equals("=")) {
            // 赋值操作
            // 获取被赋值的对象res
            leftArr = true;
            this.visit(binaryExpression.expr1);
            res = map.get(binaryExpression.expr1);
            leftArr = false;

            // 判断源操作数类型, 为了避免出现a = b + c; 生成两个四元式：tmp1 = b + c; a = tmp1;的情况。也可以用别的方法解决
            if (binaryExpression.expr2 instanceof ASTIdentifier || binaryExpression.expr2 instanceof ASTIntegerConstant ||
                    binaryExpression.expr2 instanceof ASTFloatConstant || binaryExpression.expr2 instanceof ASTCharConstant ||
                    binaryExpression.expr2 instanceof ASTStringConstant) {
                opnd1 = binaryExpression.expr2;
            } else if (binaryExpression.expr2 instanceof ASTBinaryExpression) {
                ASTBinaryExpression value = (ASTBinaryExpression) binaryExpression.expr2;
                op = value.op.value;
                visit(value.expr1);
                opnd1 = map.get(value.expr1);
                visit(value.expr2);
                opnd2 = map.get(value.expr2);
            } else if (binaryExpression.expr2 instanceof ASTPostfixExpression || binaryExpression.expr2 instanceof ASTUnaryExpression ||
                    binaryExpression.expr2 instanceof ASTFunctionCall || binaryExpression.expr2 instanceof ASTArrayAccess) {
                this.visit(binaryExpression.expr2);
                opnd1 = map.get(binaryExpression.expr2);
            }

            // build quat
            Quat quat = new Quat(op, res, opnd1, opnd2);
            quats.add(quat);
            map.put(binaryExpression, res);

            if (binaryExpression.expr1 instanceof ASTArrayAccess) {
                Quat quat1 = new Quat("[]=", arr.getOpnd2(), arr.getRes(), arr.getOpnd1());
                quats.add(quat1);
            }

        } else if (op.equals("+") || op.equals("-") ||
                op.equals("*") || op.equals("/") ||
                op.equals("%") || op.equals(">=") ||
                op.equals("<=") || op.equals(">") ||
                op.equals("<") || op.equals("==") ||
                op.equals("!=") || op.equals("&&") ||
                op.equals("||")) {
            // 双目运算操作，结果存储到中间变量
            res = new TemporaryValue(++tmpId);
            visit(binaryExpression.expr1);
            opnd1 = map.get(binaryExpression.expr1);
            visit(binaryExpression.expr2);
            opnd2 = map.get(binaryExpression.expr2);

            String name = ((TemporaryValue) res).name();
            String type = "char";
            if (opnd1 instanceof ASTFloatConstant || opnd2 instanceof ASTFloatConstant) {
                type = "float";
            } else if (opnd1 instanceof ASTIntegerConstant || opnd2 instanceof ASTIntegerConstant) {
                type = "int";
            }

            if (this.globalSymbolTable == this.localSymbolTable) {
                this.globalSymbolTable.addVariable(name, type);
            } else {
                this.localSymbolTable.addVariable(name, type);
            }

            // build quat
            Quat quat = new Quat(op, res, opnd1, opnd2);
            quats.add(quat);
            map.put(binaryExpression, res);

        } else if (op.equals("+=") || op.equals("-=") || op.equals("*=") ||
                op.equals("/=") || op.equals("%=")) {
            this.visit(binaryExpression.expr1);
            opnd1 = map.get(binaryExpression.expr1);
            this.visit(binaryExpression.expr2);
            opnd2 = map.get(binaryExpression.expr2);

            // build quat
            Quat quat = new Quat(op, opnd1, opnd1, opnd2);
            quats.add(quat);
            map.put(binaryExpression, res);
        }

    }

    @Override
    public void visit(ASTBreakStatement breakStat) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTContinueStatement continueStatement) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTCastExpression castExpression) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTCharConstant charConst) throws Exception {
        // TODO Auto-generated method stub
        map.put(charConst, charConst);
    }

    @Override
    public void visit(ASTCompoundStatement compoundStat) throws Exception {
        for (ASTNode node : compoundStat.blockItems) {
            if (node instanceof ASTDeclaration) {
                this.visit((ASTDeclaration) node);
            } else if (node instanceof ASTStatement) {
                this.visit((ASTStatement) node);
            }
        }

    }

    @Override
    public void visit(ASTConditionExpression conditionExpression) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ASTExpression expression) throws Exception {
        if (expression instanceof ASTArrayAccess) {
            visit((ASTArrayAccess) expression);
        } else if (expression instanceof ASTBinaryExpression) {
            visit((ASTBinaryExpression) expression);
        } else if (expression instanceof ASTCastExpression) {
            visit((ASTCastExpression) expression);
        } else if (expression instanceof ASTCharConstant) {
            visit((ASTCharConstant) expression);
        } else if (expression instanceof ASTConditionExpression) {
            visit((ASTConditionExpression) expression);
        } else if (expression instanceof ASTFloatConstant) {
            visit((ASTFloatConstant) expression);
        } else if (expression instanceof ASTFunctionCall) {
            visit((ASTFunctionCall) expression);
        } else if (expression instanceof ASTIdentifier) {
            visit((ASTIdentifier) expression);
        } else if (expression instanceof ASTIntegerConstant) {
            visit((ASTIntegerConstant) expression);
        } else if (expression instanceof ASTMemberAccess) {
            visit((ASTMemberAccess) expression);
        } else if (expression instanceof ASTPostfixExpression) {
            visit((ASTPostfixExpression) expression);
        } else if (expression instanceof ASTStringConstant) {
            visit((ASTStringConstant) expression);
        } else if (expression instanceof ASTUnaryExpression) {
            visit((ASTUnaryExpression) expression);
        } else if (expression instanceof ASTUnaryTypename) {
            visit((ASTUnaryTypename) expression);
        }
    }

    @Override
    public void visit(ASTExpressionStatement expressionStat) throws Exception {
        for (ASTExpression node : expressionStat.exprs) {
            visit((ASTExpression) node);
        }
    }

    @Override
    public void visit(ASTFloatConstant floatConst) throws Exception {
        // TODO Auto-generated method stub
        map.put(floatConst, floatConst);
    }

    // ("call", functionName, , ) / ("call", functionname, , temp)
    // ("arg", type, functionName, name)
    @Override
    public void visit(ASTFunctionCall funcCall) throws Exception {
        // TODO Auto-generated method stub
        String functionName = ((ASTIdentifier) funcCall.funcname).value;
        DescriptionLabel functionLabel = new DescriptionLabel(functionName);

        // 打印参数四元式
        for (ASTExpression expr : funcCall.argList) {
            this.visit(expr);
            ASTNode arg = map.get(expr);

            Quat quat = new Quat("arg", arg, functionLabel, null);
            quats.add(quat);
        }

        String returnType = this.functionTable.getArrayType(functionName);
        if (returnType == null) {    // 系统函数
            if (functionName.equals("Mars_PrintStr") || functionName.equals("Mars_PrintInt")) {
                Quat quat = new Quat("call", null, funcCall.funcname, null);
                quats.add(quat);
            } else if (functionName.equals("Mars_GetInt")) {
                ASTNode tmp = new TemporaryValue(++tmpId);
                if (this.globalSymbolTable == this.localSymbolTable) {
                    this.globalSymbolTable.addVariable(((TemporaryValue) tmp).name(), "int");
                } else {
                    this.localSymbolTable.addVariable(((TemporaryValue) tmp).name(), "int");
                }
                Quat quat = new Quat("call", tmp, funcCall.funcname, null);
                quats.add(quat);
                map.put(funcCall, tmp);
            }
        } else if (returnType.equals("void")) {    // 自定义无返回值
            Quat quat = new Quat("call", null, funcCall.funcname, null);
            quats.add(quat);
        } else {    // 自定义有返回值
            ASTNode temp = new TemporaryValue(++tmpId);
            String name = ((TemporaryValue) temp).name();
            if (this.globalSymbolTable == this.localSymbolTable) {
                this.globalSymbolTable.addVariable(name, returnType);
            } else {
                this.localSymbolTable.addVariable(name, returnType);
            }
            Quat quat = new Quat("call", temp, funcCall.funcname, null);
            quats.add(quat);
            map.put(funcCall, temp);
        }

    }

    @Override
    public void visit(ASTGotoStatement gotoStat) throws Exception {
        // TODO Auto-generated method stub
        String labelName = gotoStat.label.value;
        ASTNode label = this.labelTable.get(labelName);
        Quat quat = new Quat("jmp", label, null, null);
        quats.add(quat);
    }

    @Override
    public void visit(ASTIdentifier identifier) throws Exception {
        map.put(identifier, identifier);
    }

    @Override
    public void visit(ASTInitList initList) throws Exception {
        // TODO Auto-generated method stub
        if (initList.declarator instanceof ASTVariableDeclarator) {
            this.visit((ASTVariableDeclarator) initList.declarator);
            for (ASTExpression expr : initList.exprs) {
                this.visit(expr);
            }
        } else if (initList.declarator instanceof ASTArrayDeclarator) {
            this.visit((ASTArrayDeclarator) initList.declarator);
        } else if (initList.declarator instanceof ASTFunctionDeclarator) {
            this.visit((ASTFunctionDeclarator) initList.declarator);
        }
    }

    @Override
    public void visit(ASTIntegerConstant intConst) throws Exception {
        map.put(intConst, intConst);
    }

    // ("iterationBegin", , , scopeName) ("iterationEnd", , , scopeName)
    // 顺序：init -> (CheckCondLabel) -> cond -> (jmpCondFalse) -> state -> step -> (jmpCheckCond) -> (CondFalseLabel)
    @Override
    public void visit(ASTIterationDeclaredStatement iterationDeclaredStat) throws Exception {
        // TODO Auto-generated method stub
        if (iterationDeclaredStat == null) {
            return;
        }

        iterationDeclaredStat.scope = this.localSymbolTable;
        this.localSymbolTable = new SymbolTable();
        this.localSymbolTable.father = iterationDeclaredStat.scope;

        String scopeBeginName = "iterationScope" + this.iterationId++;
        DescriptionLabel iterationScope = new DescriptionLabel(scopeBeginName);
        Quat quat = new Quat("iterationBegin", iterationScope, null, null);
        quats.add(quat);

        if (iterationDeclaredStat.init != null) {
            this.visit(iterationDeclaredStat.init);
        }

        String condInL = "iterationCondInLabel" + this.jmpLabelId++;
        DescriptionLabel condInLabel = new DescriptionLabel(condInL, this.quats.size());
        Quat quat1 = new Quat("label", condInLabel, null, null);
        quats.add(quat1);

        if (iterationDeclaredStat.cond != null) {
            for (ASTExpression cond : iterationDeclaredStat.cond)
                this.visit(cond);
        }

        String gotoIterationEnd = "iterationGotoEnd" + this.jmpLabelId++;
        DescriptionLabel gotoIterationEndLabel = new DescriptionLabel(gotoIterationEnd);

        ASTNode val = map.get(iterationDeclaredStat.cond.get(0));

        Quat quat2 = new Quat("jf", gotoIterationEndLabel, val, null);
        quats.add(quat2);

        this.visit(iterationDeclaredStat.stat);

        String gotocondCheck = "iterationGotoCond" + this.jmpLabelId++;
        DescriptionLabel gotocondCheckLabel = new DescriptionLabel(gotocondCheck, this.quats.size());

        Quat quat3 = new Quat("label", gotocondCheckLabel, null, null);
        quats.add(quat3);

        if (iterationDeclaredStat.step != null) {
            for (ASTExpression step : iterationDeclaredStat.step)
                this.visit(step);
        }

        Quat quat4 = new Quat("jmp", condInLabel, null, null);
        quats.add(quat4);

        Quat quat5 = new Quat("label", gotoIterationEndLabel, null, null);
        quats.add(quat5);

        String scopeEndName = "iterationScopeBegin" + this.iterationId++;
        Quat quat6 = new Quat("iterationEnd", iterationScope, null, null);
        quats.add(quat6);

        // 符号表切换
        this.localSymbolTable = iterationDeclaredStat.scope;

    }

    @Override
    public void visit(ASTIterationStatement iterationStat) throws Exception {
        // TODO Auto-generated method stub
        if (iterationStat == null) {
            return;
        }

        iterationStat.scope = this.localSymbolTable;
        this.localSymbolTable = new SymbolTable();
        this.localSymbolTable.father = iterationStat.scope;

        String scopeBeginName = "iterationScope" + this.iterationId++;
        DescriptionLabel iterationScope = new DescriptionLabel(scopeBeginName);
        Quat quat = new Quat("iterationBegin", iterationScope, null, null);
        quats.add(quat);

        if (iterationStat.init != null) {
            for (ASTExpression init : iterationStat.init)
                this.visit(init);
        }

        String condInL = "iterationCondInLabel" + this.jmpLabelId++;
        DescriptionLabel condInLabel = new DescriptionLabel(condInL, this.quats.size());
        Quat quat1 = new Quat("label", condInLabel, null, null);
        quats.add(quat1);

        if (iterationStat.cond != null) {
            for (ASTExpression cond : iterationStat.cond)
                this.visit(cond);
        }

        String gotoIterationEnd = "iterationGotoEnd" + this.jmpLabelId++;
        DescriptionLabel gotoIterationEndLabel = new DescriptionLabel(gotoIterationEnd);

        ASTNode val = map.get(iterationStat.cond.get(0));
        Quat quat2 = new Quat("jf", gotoIterationEndLabel, val, null);
        quats.add(quat2);

        this.visit(iterationStat.stat);

        String gotocondCheck = "iterationGotoCond" + this.jmpLabelId++;
        DescriptionLabel gotocondCheckLabel = new DescriptionLabel(gotocondCheck, this.quats.size());

        Quat quat3 = new Quat("label", gotocondCheckLabel, null, null);
        quats.add(quat3);

        if (iterationStat.step != null) {
            for (ASTExpression step : iterationStat.step)
                this.visit(step);
        }

        Quat quat4 = new Quat("jmp", condInLabel, null, null);
        quats.add(quat4);

        Quat quat5 = new Quat("label", gotoIterationEndLabel, null, null);
        quats.add(quat5);

        String scopeEndName = "iterationScopeBegin" + this.iterationId++;
        Quat quat6 = new Quat("iterationEnd", iterationScope, null, null);
        quats.add(quat6);

        // 符号表切换
        this.localSymbolTable = iterationStat.scope;

    }

    @Override
    public void visit(ASTLabeledStatement labeledStat) throws Exception {
        // TODO Auto-generated method stub
        String label = labeledStat.label.value;
        DescriptionLabel inLabel = new DescriptionLabel(label, this.quats.size());
        Quat quat = new Quat("label", inLabel, null, null);
        quats.add(quat);
        this.labelTable.put(label, inLabel);

        this.visit(labeledStat.stat);

    }

    @Override
    public void visit(ASTMemberAccess memberAccess) throws Exception {
        // TODO Auto-generated method stub

    }

    // a++, a--, a., a->
    // ("=", expr, , temp)
    // ("op", temp, , expr)
    @Override
    public void visit(ASTPostfixExpression postfixExpression) throws Exception {
        // TODO Auto-generated method stub
        String op = postfixExpression.op.value;
        ASTNode temp = new TemporaryValue(++tmpId);

        String name = ((TemporaryValue) temp).name();
        if (this.globalSymbolTable == this.localSymbolTable) {
            this.globalSymbolTable.addVariable(name, "int");
        } else {
            this.localSymbolTable.addVariable(name, "int");
        }

        Quat quat = new Quat("=", temp, postfixExpression.expr, null);
        quats.add(quat);
        Quat quat1 = new Quat(op, postfixExpression.expr, null, null);
        quats.add(quat1);

        map.put(postfixExpression, temp);

    }

    // ("ret", , , ) ("ret", , , returnValue)
    @Override
    public void visit(ASTReturnStatement returnStat) throws Exception {
        // TODO Auto-generated method stub
        // return ;
        if (returnStat.expr == null) {
            Quat quat = new Quat("ret", null, null, null);
            quats.add(quat);
        } else {
            for (ASTExpression expr : returnStat.expr) {
                this.visit(expr);
            }
            ASTNode ret = map.get(returnStat.expr.get(0));
            Quat quat = new Quat("ret", ret, null, null);
            quats.add(quat);
        }

    }

    // cond -> judge -> jmpFalse -> then -> jmpEnd-> falseLabel -> otherwise -> endLabel
    // ("ifBegin", , , ), ..., ("jf", cond, , falseLabel), ..., ("jmp", , , endLabel),
    // ("label", , , falseLabel), ..., ("label", , , endLabel)
    @Override
    public void visit(ASTSelectionStatement selectionStat) throws Exception {
        // TODO Auto-generated method stub

        for (ASTExpression cond : selectionStat.cond) {
            this.visit(cond);
        }

        ASTNode res = map.get(selectionStat.cond.get(0));
        String label1 = "ifCondFalseLabel" + this.jmpLabelId++;
        DescriptionLabel ifCondFalseLabel = new DescriptionLabel(label1);
        Quat quat = new Quat("jf", ifCondFalseLabel, res, null);
        quats.add(quat);

        this.visit(selectionStat.then);

        String label2 = "gotoEndLabel" + this.jmpLabelId++;
        DescriptionLabel gotoEndLabel = new DescriptionLabel(label2);
        Quat quat1 = new Quat("jmp", gotoEndLabel, null, null);
        quats.add(quat1);

        ifCondFalseLabel.destination = this.quats.size();
        Quat quat2 = new Quat("label", ifCondFalseLabel, null, null);
        quats.add(quat2);

        if (selectionStat.otherwise != null) {
            this.visit(selectionStat.otherwise);
        }

        gotoEndLabel.destination = this.quats.size();
        Quat quat3 = new Quat("label", gotoEndLabel, null, null);
        quats.add(quat3);

    }

    @Override
    public void visit(ASTStringConstant stringConst) throws Exception {
        // TODO Auto-generated method stub
        map.put(stringConst, stringConst);
    }

    @Override
    public void visit(ASTTypename typename) throws Exception {
        // TODO Auto-generated method stub

    }

    // ++a, --a: (op, a, , a)
    // &a, *a, +a, -a, !a, sizeof(a): (op, a, , temp)
    @Override
    public void visit(ASTUnaryExpression unaryExpression) throws Exception {
        // TODO Auto-generated method stub
        String op = unaryExpression.op.value;
        if (op.equals("++") || op.equals("--")) {
            this.visit(unaryExpression.expr);

            // (op, a, , a)
            ASTNode res = map.get(unaryExpression.expr);
            Quat quat = new Quat(op, res, null, null);
            quats.add(quat);
            map.put(unaryExpression, res);
        } else {
            // TODO &a, *a, +a, -a, !a, sizeof(a)
        }

    }

    @Override
    public void visit(ASTUnaryTypename unaryTypename) throws Exception {
        // TODO Auto-generated method stub

    }

    // ("func", returnType, argNum, funcName)
    // ("param", type, , argName)
    @Override
    public void visit(ASTFunctionDefine functionDefine) throws Exception {
        ASTToken typeSpecifier = functionDefine.specifiers.get(0);

        functionDefine.scope = this.localSymbolTable;
        this.localSymbolTable = new SymbolTable();

        String funcname = functionDefine.declarator.getName();
        DescriptionLabel funcLabel = new DescriptionLabel(funcname);
        ASTIntegerConstant paramNum = new ASTIntegerConstant(((ASTFunctionDeclarator) functionDefine.declarator).params.size(), -1);
        Quat quat = new Quat("func", typeSpecifier, paramNum, funcLabel);
        quats.add(quat);

        ASTFunctionDeclarator astFunctionDeclarator = (ASTFunctionDeclarator) functionDefine.declarator;
        LinkedList<ASTNode> params = new LinkedList<>();
        for (ASTParamsDeclarator param : astFunctionDeclarator.params) {
            ASTToken paramTypeSpecifier = param.specfiers.get(0);

            String paramName = param.declarator.getName();
            this.localSymbolTable.addVariable(paramName, paramTypeSpecifier.value);
            params.add(param);

            DescriptionLabel paramLabel = new DescriptionLabel(paramName);
            Quat quat1 = new Quat("param", paramLabel, paramTypeSpecifier, null);
            quats.add(quat1);
        }

        this.functionTable.addVariable(funcname, typeSpecifier.value, params);

        this.visit(functionDefine.declarator);
        this.visit(functionDefine.body);

        Quat quat1 = new Quat("funcEnd", functionDefine.declarator, null, null);
        quats.add(quat1);

        this.localSymbolTable = this.globalSymbolTable;
    }

    @Override
    public void visit(ASTDeclarator declarator) throws Exception {
        // TODO Auto-generated method stub
        if (declarator instanceof ASTVariableDeclarator) {
            visit((ASTVariableDeclarator) declarator);
        } else if (declarator instanceof ASTArrayDeclarator) {
            visit((ASTArrayDeclarator) declarator);
        } else if (declarator instanceof ASTFunctionDeclarator) {
            visit((ASTFunctionDeclarator) declarator);
        }
    }

    @Override
    public void visit(ASTStatement statement) throws Exception {
        if (statement instanceof ASTIterationDeclaredStatement) {
            visit((ASTIterationDeclaredStatement) statement);
        } else if (statement instanceof ASTIterationStatement) {
            visit((ASTIterationStatement) statement);
        } else if (statement instanceof ASTCompoundStatement) {
            visit((ASTCompoundStatement) statement);
        } else if (statement instanceof ASTSelectionStatement) {
            visit((ASTSelectionStatement) statement);
        } else if (statement instanceof ASTExpressionStatement) {
            visit((ASTExpressionStatement) statement);
        } else if (statement instanceof ASTBreakStatement) {
            visit((ASTBreakStatement) statement);
        } else if (statement instanceof ASTContinueStatement) {
            visit((ASTContinueStatement) statement);
        } else if (statement instanceof ASTReturnStatement) {
            visit((ASTReturnStatement) statement);
        } else if (statement instanceof ASTGotoStatement) {
            visit((ASTGotoStatement) statement);
        } else if (statement instanceof ASTLabeledStatement) {
            visit((ASTLabeledStatement) statement);
        }
    }

    @Override
    public void visit(ASTToken token) throws Exception {
        // TODO Auto-generated method stub

    }

}
