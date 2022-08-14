grammar GrammarDesign;

import CommonLexerRule;


// 表达式
expression:
    assignmentExpression
    | expression ',' assignmentExpression
    ;

// 赋值语句
assignmentExpression:
    conditionalExpression
    | unaryExpression assignmentOperator assignmentExpression
    ;

assignmentOperator:
    '=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '&=' | '^=' | '|='
    ;

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
    castExpression
    | multiplicativeExpression '*' castExpression
    | multiplicativeExpression '/' castExpression
    | multiplicativeExpression '%' castExpression
    ;

castExpression:
    unaryExpression
    | '(' typeName ')' castExpression
    ;

unaryExpression:
    postfixExpression
    | '++' unaryExpression
    | '--' unaryExpression
    | unaryOperator castExpression
    | 'sizeof' unaryExpression
    | 'sizeof' '(' typeName ')'
    | '_Alignof' '(' typeName ')'
    ;

unaryOperator:
    '&' | '*' | '+' | '-' | '~' | '!'
    ;

postfixExpression:
    primaryExpression
    | postfixExpression '[' expression ']'
    | postfixExpression '(' argumentExpressionList? ')'
    | postfixExpression '.' identifier
    | postfixExpression '->' identifier
    | postfixExpression '++'
    | postfixExpression '--'
    | '(' typeName ')' '{' initializerList '}'
    | '(' typeName ')' '{' initializerList ',' '}'
    | '__extension__' '(' typeName ')' '{' initializerList '}'
    | '__extension__' '(' typeName ')' '{' initializerList ',' '}'
    ;

primaryExpression:
    identifier
    | constant
    | stringLiteral
    | '(' expression ')'
    ;

identifier:
    NonDigit
    | identifier NonDigit
    | identifier Digit
    ;


typeName:
    specifierQualifierList abstractDeclarator?
    ;

specifierQualifierList:
    typeSpecifier specifierQualifierList?
    | typeQualifier specifierQualifierList?
    ;

typeSpecifier:
    'void' | 'char' | 'short' | 'int' | 'long' | 'float' | 'double' | 'signed' | 'unsigned'
    ;

typeQualifier:
    'const' | 'restrict' | 'volatile'
    ;

abstractDeclarator:
    pointer
    | pointer? directAbstractDeclarator
    ;

pointer:
    '*' typeQualifierList?
    | '*' typeQualifierList? pointer
    ;

typeQualifierList:
    typeQualifier
    | typeQualifierList typeQualifier
    ;

directAbstractDeclarator:
    '(' abstractDeclarator ')'
    | directAbstractDeclarator? '[' typeQualifierList? assignmentExpression? ']'
    | directAbstractDeclarator? '[' 'static' typeQualifierList? assignmentExpression ']'
    | directAbstractDeclarator? '[' typeQualifierList 'static' assignmentExpression ']'
    | directAbstractDeclarator? '[' '*' ']'
    | directAbstractDeclarator? '(' parameterTypeList? ')'
    ;




