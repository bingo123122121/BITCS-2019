grammar CSubsetGrammarDesign;

import CommonLexerRule;

// 入口
translationUnit:
    externalDeclaration
    | translationUnit externalDeclaration
    ;

externalDeclaration:
    functionDefinition
    | declaration expression ';'
    ;

functionDefinition:
    declaration (Identifier | MAIN) '(' declarationList? ')' block
    ;

declarationList:
    declaration
    | declarationList declaration
    ;

block:
    statement block?
    | '{' statement block? '}'
    ;

statement:
    declaration? expression ';'
    | selectionStatement
    | iterationStatement
    | jumpStatement ';'
    ;

// 表达式
expression:
    assignmentExpression
    | expression ',' assignmentExpression
    ;

// 分支语句
selectionStatement:
    IF '(' expression ')' block
    | IF '(' expression ')' block ELSE block
    ;

// 循环语句
iterationStatement:
    WHILE '(' expression ')' block
    | FOR '(' (declaration? expression)? ';' expression? ';' expression? ')' block
    | FOR '(' (declaration expression)? ';' expression? ')' block
    ;

// 跳转语句
jumpStatement:
    CONTINUE
    | BREAK
    | RETURN expression?
    ;

// 赋值语句
assignmentExpression:
    conditionalExpression
    | Identifier assignmentOperator assignmentExpression
    ;

// 运算符优先级
conditionalExpression:
    logicalOrExpression
    | logicalOrExpression '?' expression ':' conditionalExpression
    ;

logicalOrExpression:
    logicalAndExpression
    | logicalOrExpression '||' logicalAndExpression
    ;

logicalAndExpression:
    inclusiveOrExpression
    | logicalAndExpression '&&' inclusiveOrExpression
    ;

inclusiveOrExpression:
    exclusiveOrExpression
    | inclusiveOrExpression '|' exclusiveOrExpression
    ;

exclusiveOrExpression:
    andExpression
    | exclusiveOrExpression '^' andExpression
    ;

andExpression:
    equalityExpression
    | andExpression '&' equalityExpression
    ;

equalityExpression:
    relationalExpression
    | equalityExpression '==' relationalExpression
    | equalityExpression '!=' relationalExpression
    ;

relationalExpression:
    shiftExpression
    | relationalExpression '<' shiftExpression
    | relationalExpression '>' shiftExpression
    | relationalExpression '<=' shiftExpression
    | relationalExpression '>=' shiftExpression
    ;

shiftExpression:
    additiveExpression
    | shiftExpression '<<' additiveExpression
    | shiftExpression '>>' additiveExpression
    ;

additiveExpression:
    multiplicativeExpression
    | additiveExpression '+' multiplicativeExpression
    | additiveExpression '-' multiplicativeExpression
    ;

multiplicativeExpression:
    primaryExpression
    | multiplicativeExpression '*' primaryExpression
    | multiplicativeExpression '/' primaryExpression
    | multiplicativeExpression '%' primaryExpression
    ;

primaryExpression:
    Identifier
    | Constant
    | StringLiteral
    | '(' expression ')'
    ;

assignmentOperator:
    '=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '&=' | '^=' | '|='
    ;

declaration:
    TypeSpecifier declaration?
    ;

// 换行空格等直接删除
WS : [ \t\r\n]+ -> skip;



