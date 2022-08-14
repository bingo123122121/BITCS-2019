lexer grammar CommonLexerRule;

// 类型名
TypeSpecifier: 'void' | 'int' | 'float' | 'double' | 'char' | 'string' | 'signed' | 'unsigned';

// 关键词
MAIN: 'main';
WHILE: 'while';
FOR: 'for';
IF: 'if';
ELSE: 'else';
CONTINUE: 'continue';
BREAK: 'break';
RETURN: 'return';

// 标识符
Identifier: NonDigit (NonDigit | Digit)*;

// 常量
Constant : IntegerConstant | FloatConstant | CharConstant;

// 字符串字面量
StringLiteral : ('u8' | 'u' | 'U' | 'L')? '"' (Char | EscapeChar | OctalEscapeChar | HexadecimalEscapeChar)+ '"';

// 字母表
Character: [a-zA-Z];
Digit: [0-9];

// 终结符定义
NonDigit: [_a-zA-Z];

NonzeroDigit : [1-9];
OctalDigit : [0-7];
NonzeroOctalDigit : [1-7];
HexadecimalDigit : [0-9a-fA-F];
NonzeroHexadecimalDigit : [1-9a-fA-F];

IntegerSuffix : ([Uu]('L' | 'l' | 'LL' | 'll')) | (('L' | 'l' | 'LL' | 'll')[Uu]);

UnsignedInteger : (NonzeroDigit Digit*) | '0';
DecimalIntegerConstant : UnsignedInteger IntegerSuffix?;
UnsignedOctalInteger : NonzeroOctalDigit OctalDigit* | '0';
OctalIntegerConstant : '0' UnsignedOctalInteger IntegerSuffix?;
UnsignedHexadecimalInteger : NonzeroHexadecimalDigit HexadecimalDigit* |'0';
HexadecimalIntegerConstant : '0' [Xx] UnsignedHexadecimalInteger IntegerSuffix?;

// 整型常量
IntegerConstant : DecimalIntegerConstant | OctalIntegerConstant | HexadecimalIntegerConstant ;

FloatSuffix : 'f' | 'l' | 'F' | 'L';
DecimalFloatConstant : DecimalIntegerConstant ('.' UnsignedInteger )?(('E' | 'e')DecimalIntegerConstant)? FloatSuffix?;
HexadecimalFloatConstant : HexadecimalIntegerConstant ('.' UnsignedHexadecimalInteger )?(('P' | 'p')DecimalIntegerConstant )? FloatSuffix?;

// 浮点常量
FloatConstant : DecimalFloatConstant | HexadecimalFloatConstant ;

Char : Character | Digit | ' ' | '!' | '.' | ',' | '?';
EscapeChar : '\\' ('a' | 'b' | 'f' | 'n' | 'r' | 't' | 'v' | '"' | '\'' | '?' | '\\');
OctalEscapeChar : '\\' OctalDigit{1,3} ;
HexadecimalEscapeChar : '\\' [Xx] HexadecimalDigit{1,2} ;

// 字符常量
CharConstant : [LUu]? '\'' (Char | EscapeChar | OctalEscapeChar | HexadecimalEscapeChar) '\'';


