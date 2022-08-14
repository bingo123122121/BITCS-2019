import java.io.*;

%%

%class Scanner
%type String
%column
%line

%{

    public int line = 1;

    public enum TokenType{
        KEYWORD, IDENTIFIER, INTEGER, FLOAT,
        CHAR, STRING, OPERATOR, BOUND, BLANK, ANNOTATION;
    };

    public int getYyline(){
        return yyline;
    }

    public int getYycolumn(){
        return yycolumn;
    }

%}

// 关键词
Keyword = (auto|break|case|char|const|continue|default|do|double|else|enum|extern|float|for|goto|if|inline|
            int|long|register|restrict|return|short|signed|sizeof|static|struct|switch|typedef|union|unsigned|
            void|volatile|while)

// 运算符
Operator = "."|"->"|"++"|"--"|"&"|"*"|"+"|"-"|"~"|"!"|"/"|"%"|"<<"|">>"|"<"|">"|
            "<="|">="|"=="|"!="|"^"|"|"|"&&"|"||"|"?"|":"|";"|"..."|"="|"*="|"/="|
            "%="|"+="|"-="|"<<="|">>="|"&="|"^="|"|="|"sizeof"

// 界限符
Bound = (\[|\]|\(|\)|\{|\}|,|;|\'|\"|#)

Character = [a-zA-Z]

// 标识符
Identifier = ({Character}|_)({Character}|_|{Digit})*

Digit = [0-9]
NonzeroDigit = [1-9]
OctalDigit = [0-7]
NonzeroOctalDigit = [1-7]
HexadecimalDigit = [0-9a-fA-F]
NonzeroHexadecimalDigit = [1-9a-fA-F]

IntegerSuffix = ([Uu](L|l|LL|ll))|((L|l|LL|ll)[Uu])

UnsignedInteger = {NonzeroDigit}{Digit}*|0
DecimalIntegerConstant = ("+"|"-")?{UnsignedInteger}{IntegerSuffix}?
UnsignedOctalInteger = {NonzeroOctalDigit}{OctalDigit}*|0
OctalIntegerConstant = ("+"|"-")?0{UnsignedOctalInteger}{IntegerSuffix}?
UnsignedHexadecimalInteger = {NonzeroHexadecimalDigit}{HexadecimalDigit}*|0
HexadecimalIntegerConstant = ("+"|"-")?0[Xx]{UnsignedHexadecimalInteger}{IntegerSuffix}?

// 整型常量
IntegerConstant = {DecimalIntegerConstant}|{OctalIntegerConstant}|{HexadecimalIntegerConstant}

FloatSuffix = f|l|F|L
DecimalFloatConstant = {DecimalIntegerConstant}([\.]{UnsignedInteger})?((E|e){DecimalIntegerConstant})?{FloatSuffix}?
HexadecimalFloatConstant = {HexadecimalIntegerConstant}([\.]{UnsignedHexadecimalInteger})?((P|p){DecimalIntegerConstant})?{FloatSuffix}?

// 浮点常量
FloatConstant = {DecimalFloatConstant}|{HexadecimalFloatConstant}

Char = [\x00-\x09\x11-\x26\x28-\x5B\x5D-\x7f]
EscapeChar = \\(a|b|f|n|r|t|v|\'|\"|\?|\\)
OctalEscapeChar = \\{OctalDigit}{1,3}
HexadecimalEscapeChar = \\[Xx]{HexadecimalDigit}{1,2}

// 字符常量
CharConstant = [LUu]?\'({Char}|{EscapeChar}|{OctalEscapeChar}|{HexadecimalEscapeChar})\'

// 字符串字面量
StringLiteral = ["u8"uUL]?\"({Char}|{EscapeChar}|{OctalEscapeChar}|{HexadecimalEscapeChar})+\"

%%

\n {++line;}

{Keyword} {return TokenType.KEYWORD.name();} 

{Identifier} {return TokenType.IDENTIFIER.name();} 

{IntegerConstant} {return TokenType.INTEGER.name();} 
{FloatConstant} {return TokenType.FLOAT.name();} 

{CharConstant} {return TokenType.CHAR.name();} 
{StringLiteral} {return TokenType.STRING.name();} 

{Operator} {return TokenType.OPERATOR.name();} 
{Bound} {return TokenType.BOUND.name();} 

[ \t]+ {return TokenType.BLANK.name();}

"//"[^\n]*\n {return TokenType.ANNOTATION.name();}   // 识别单行注释
"/*"([^*]|\*+[^/*])*"*/" {return TokenType.ANNOTATION.name();}   // 识别多行注释
<<EOF>> {return "EOF";}