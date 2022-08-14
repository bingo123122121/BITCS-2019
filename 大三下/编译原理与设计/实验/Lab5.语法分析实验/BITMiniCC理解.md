# BITMiniCC结点类型

没有值的属性，数组用[]表示，其他用null表示

* 表达式结点：
  * ASTExpression，表达式及基本类型入口
    * ASTUnaryExpression，一元运算表达式结点，包含op、expr
    * ASTUnaryTypename，sizeof(类型)运算，包含op、typename
    * ASTBinaryExpression，二元运算表达式，包含op、expr1、expr2
    * ASTFunctionCall，函数调用，包含funcname、argList
    * ASTArrayAccess，数组访问，包含arrayName、elements
    * ASTPostfixExpression，后缀表达式，包含expr、op
    * ASTCastExpression，显式类型转换表达式，包含typename、expr
    * ASTConditionExpression，条件运算表达式，包含condExpr、trueExpr、falseExpr
    * ASTMemberAccess，成员访问，包含master、op、member
* 语句结点：
  * ASTStatement，语句入口
    * ASTBreakStatement，break语句，无属性
    * ASTContinueStatement，continue语句，无属性
    * ASTGotoStatement，goto语句，无属性
    * ASTReturnStatement，return语句，无属性
    * ASTLabeledStatement，标志语句，包含label、stat，eg：k : break;
    * ASTCompoundStatement，复合语句，包含blockItems，eg：{ goto k; break; }
    * ASTExpressionStatement，表达式语句，包含exprs
    * ASTSelectionStatement，选择语句，包含cond、then、otherwise，仅if-else
    * ASTIterationStatement，无声明的循环语句，包含init、cond、step、stat，仅for
    * ASTIterationDeclaredStatement，有声明的循环语句，包含init、cond、step、stat，仅for
* 声明结点：
  * ASTDeclarator，包含type、declarator
    * ASTVariableDeclarator，变量声明，主要用于引用变量名，包含identifier，eg：a
    * ASTArrayDeclarator，数组声明，主要用于引用数组元素，包含declarator、qualifierList、expr，eg：a[2]
    * ASTFunctionDeclarator，函数声明，主要用于引用函数名，包含declarator、params，eg：func(double, int a)
    * ASTParamsDeclarator，参数声明，主要用于引用参数名，包含specfiers、declarator，eg：func(double, int a)中double、int a
  * ASTInitList，初始化列表，包含declarator、exprs
  * ASTDeclaration，声明语句，包含specifiers、initLists
* 叶结点：所有叶结点都需要包括type、value、tokenId
  * ASTIntegerConstant、ASTCharConstant、ASTFloatConstant、ASTStringLiteral，常量结点
  * ASTIdentifier，标识符结点
  * ASTToken，标记结点，主要为数据类型、运算符等关键词，如int、double、void、<、++
* 其它结点：
  * 函数定义：ASTFunctionDefine，包含specifiers、declarator、declarations、body
  * 语法分析入口：ASTCompilationUnit
  * 类型名：ASTTypename，只出现在ASTUnaryTypename、ASTCastExpression，包含specfiers、declarator



# 语法产生式

- program -> functionList
- functionList -> functionDefine functionList | e
- functionDefine -> typeSpecifiers declarator '(' arguments ')' compoundStatement
  - typeSpecifiers -> typeSpecifier typeSpecifiers | e
    - typeSpecifier -> 'void' | 'int' | 'float' | 'double' | 'char' | 'string' | 'signed' | 'unsigned'
  - arguments -> argumentList | e
    - argumentList -> arguement ',' argumentList | argument
    - argument ->typeSpecifiers declarator
  - compoundStatement -> '{' blockItemList '}'
    * blockItemList -> declaration blockItemList | statement blockItemList | e
      * declaration -> typeSpecifiers initDeclaratorList ';'
- initDeclaratorList -> initDeclarator | initDeclarator ',' initDeclaratorList
  - initDeclarator -> declarator | declarator '=' initializer
    - declarator -> identifier postDeclarator
      - postDeclarator -> '[' assignmentExpression ']' postDeclarator | '[' ']' postDeclarator | e
    - initializer -> assignmentExpression | '{' expression '}'
- statement -> breakStatement | continueStatement | gotoStatement | returnStatement | compoundStatement | selectionStatement | iterationStatement | iterationDeclaredStatement | expressionStatement
  - breakStatement -> 'break' ';'
  - continueStatement -> 'continue' ';'
  - gotoStatement -> 'goto' identifier ';'
  - returnStatement -> 'return' expression ';' | 'return' ';'
  - selectionStatement -> 'if' '(' expression ')' statement | 'if' '(' expression ')' statement 'else' statement 
  - iterationStatement -> 'for' '(' expression ';' expression ';' expression ')' statement  | 'for' '(' expression ';' expression ';' ')' statement
    - iterationStatement -> 'for' '(' declaration ';' expression ';' expression ')' statement  | 'for' '(' declaration ';' expression ';' ')' statement
  - expressionStatement -> expression ';' | ';'
    - expression -> assignmentExpression ',' expressionStatement | assignmentExpression
- assignmentExpression -> conditionalExpression | unaryExpression assignmentOperator assignmentExpression
  - assignmentOperator -> '=' | '*=' | '/=' | '%=' | '+=' | '-='
  - conditionalExpression -> logicalOrExpression | logicalOrExpression '?' expression ':' conditionalExpression
  - logicalOrExpression -> logicalAndExpression | logicalAndExpression '||' logicalOrExpression
  - logicalAndExpression -> equalityExpression | equalityExpression '&&' logicalAndExpression
  - equalityExpression -> relationalExpression | relationalExpression '==' equalityExpression | relationalExpression '!=' equalityExpression
  - relationalExpression -> additiveExpression | additiveExpression '<' relationalExpression | additiveExpression '>' relationalExpression | additiveExpression '<=' relationalExpression | additiveExpression '>=' relationalExpression
  - additiveExpression -> multiplicativeExpression | multiplicativeExpression '+' additiveExpression | multiplicativeExpression '-' additiveExpression
  - multiplicativeExpression -> castExpression | castExpression '*' multiplicativeExpression |  castExpression '/' multiplicativeExpression | castExpression '%' multiplicativeExpression
  - castExpression -> unaryExpression | '(' typeSpecifiers ')' castExpression
  - unaryExpression -> postfixExpression | unaryOperator castExpression
    - unaryOperator -> '++' | '--' | '&' | '*' | '+' | '-' | '!' | 'sizeof'
  - postfixExpression -> primaryExpression postfixExpressionPost
    - postfixExpressionPost -> '[' expression ']' postfixExpressionPost | '(' expression ')' postfixExpressionPost |  '(' ')' postfixExpressionPost | '.' postfixExpressionPost | '->' postfixExpressionPost | '++' postfixExpressionPost | '--' postfixExpressionPost | e
  - primaryExpression -> identifier | integerConstant | floatingConstant | characterConstant | stringLiteral | '(' expression ')'











