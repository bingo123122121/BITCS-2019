package bit.minisys.minicc.parser;

import bit.minisys.minicc.MiniCCCfg;
import bit.minisys.minicc.internal.util.MiniCCUtil;
import bit.minisys.minicc.parser.ast.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.antlr.v4.gui.TreeViewer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
- program -> functionList

- functionList -> functionDefine functionList | e

- functionDefine -> typeSpecifiers declarator '(' arguments ')' compoundStatement

  - typeSpecifiers -> TypeSpecifier typeSpecifiers | e
    - typeSpecifier -> 'void' | 'int' | 'float' | 'double' | 'char' | 'string' | 'signed' | 'unsigned'
  - arguments -> argumentList | e
    - argumentList -> arguement ',' argumentList | argument
      - argument -> typeSpecifiers declarator
  - compoundStatement -> '{' blockItemList '}'
  - blockItemList -> declaration blockItemList | statement blockItemList | e

- declaration -> typeSpecifiers initDeclaratorList ';'
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

- expression -> assignmentExpression ',' expression | assignmentExpression

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
    - unaryOperator -> '++' | '--' | '&' | '*' | '+' | '-' | '~' | '!' | 'sizeof'
  - postfixExpression -> primaryExpression postfixExpressionPost
    - postfixExpressionPost -> '[' expression ']' postfixExpressionPost | '(' expression ')' postfixExpressionPost |  '(' ')' postfixExpressionPost | '.' postfixExpressionPost | '->' postfixExpressionPost | '++' postfixExpressionPost | '--' postfixExpressionPost | e
  - primaryExpression -> Identifier | IntegerConstant | FloatingConstant | CharacterConstant | StringLiteral | '(' expression ')'


 */

class ScannerToken {
    public String lexme;
    public String type;
    public int line;
    public int column;
}

public class MyCSubsetParser implements IMiniCCParser {

    private ArrayList<ScannerToken> tknList;
    private int tokenIndex;
    private ScannerToken nextToken;

    @Override
    public String run(String iFile) throws Exception {
        System.out.println("Parsing...");

        String oFile = MiniCCUtil.removeAllExt(iFile) + MiniCCCfg.MINICC_PARSER_OUTPUT_EXT;
        String tFile = MiniCCUtil.removeAllExt(iFile) + MiniCCCfg.MINICC_SCANNER_OUTPUT_EXT;

        tknList = loadTokens(tFile);
        tokenIndex = 0;

        ASTNode root = program();


        String[] dummyStrs = new String[16];
        TreeViewer viewr = new TreeViewer(Arrays.asList(dummyStrs), root);
        viewr.open();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(oFile), root);

        //TODO: write to file


        return oFile;
    }

    // 'void' | 'int' | 'float' | 'double' | 'char' | 'string' | 'signed' | 'unsigned'
    private boolean isTypeSpecifier(String str) {
        return str.equals("'void'") || str.equals("'int'") || str.equals("'float'") || str.equals("'double'") ||
                str.equals("'char'") || str.equals("'string'") || str.equals("'signed'") || str.equals("'unsigned'");
    }

    // '=' | '*=' | '/=' | '%=' | '+=' | '-='
    public boolean isAssignmentOperator(String str) {
        return str.equals("'='") || str.equals("'*='") || str.equals("'/='") || str.equals("'%='") ||
                str.equals("'+='") || str.equals("'-='");
    }

    // '++' | '--' | '&' | '*' | '+' | '-' | '!' | 'sizeof'
    private boolean isUnaryOperator(String str) {
        return str.equals("'++'") || str.equals("'--'") || str.equals("'&'") || str.equals("'*'") ||
                str.equals("'+'") || str.equals("'-'") || str.equals("'!'") || str.equals("'sizeof'");
    }

    private ArrayList<ScannerToken> loadTokens(String tFile) {
        tknList = new ArrayList<ScannerToken>();

        ArrayList<String> tknStr = MiniCCUtil.readFile(tFile);

        for (String str : tknStr) {
            if (str.trim().length() <= 0) {
                continue;
            }

            ScannerToken st = new ScannerToken();
            //[@0,0:2='int',<'int'>,1:0]
            String[] segs;
            if (str.indexOf("<','>") > 0) {
                str = str.replace("','", "'DOT'");

                segs = str.split(",");
                segs[1] = "=','";
                segs[2] = "<','>";

            } else {
                segs = str.split(",");
            }
            st.lexme = segs[1].substring(segs[1].indexOf("=") + 1);
            st.type = segs[2].substring(segs[2].indexOf("<") + 1, segs[2].length() - 1);
            String[] lc = segs[3].split(":");
            st.line = Integer.parseInt(lc[0]);
            st.column = Integer.parseInt(lc[1].replace("]", ""));

            tknList.add(st);
        }

        return tknList;
    }

    private ScannerToken getToken(int index) {
        if (index < tknList.size()) {
            return tknList.get(index);
        }
        return null;
    }

    public void matchToken(String type) {
        if (tokenIndex < tknList.size()) {
            ScannerToken next = tknList.get(tokenIndex);
            if (!next.type.equals(type)) {
                System.out.println("[ERROR]Parser: unmatched token, expected = " + type + ", "
                        + "input = " + next.type);
            } else {
                tokenIndex++;
            }
        }
    }

    // program -> functionList
    public ASTNode program() {
        ASTCompilationUnit p = new ASTCompilationUnit();
        ArrayList<ASTNode> fl = functionList();
        if (fl != null) {
            //p.getSubNodes().add(fl);
            p.items.addAll(fl);
        }
        p.children.addAll(p.items);
        return p;
    }

    // functionList -> functionDefine functionList | e
    public ArrayList<ASTNode> functionList() {
        ArrayList<ASTNode> fl = new ArrayList<ASTNode>();

        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("EOF")) {
            return null;
        } else {
            ASTNode f = functionDefine();
            fl.add(f);
            ArrayList<ASTNode> fl2 = functionList();
            if (fl2 != null) {
                fl.addAll(fl2);
            }
            return fl;
        }
    }

    // functionDefine -> typeSpecifiers declarator '(' arguments ')' CompoundStatement
    public ASTNode functionDefine() {
        ASTFunctionDefine fdef = new ASTFunctionDefine();

        ArrayList<ASTToken> specifiers = typeSpecifiers();
        fdef.specifiers = specifiers;
        fdef.children.addAll(specifiers);

        ASTFunctionDeclarator fdecl = new ASTFunctionDeclarator();
        ASTDeclarator decl = declarator();
        fdecl.declarator = decl;
        fdecl.children.add(decl);

        matchToken("'('");

        ArrayList<ASTParamsDeclarator> pdl = arguments();
        if (pdl != null) {
            fdecl.params = pdl;
            fdecl.children.addAll(pdl);
        }
        fdef.declarator = fdecl;
        fdef.children.add(fdecl);

        matchToken("')'");

        ASTCompoundStatement body = compoundStatement();
        fdef.body = body;
        fdef.children.add(body);

        return fdef;
    }

    // typeSpecifiers -> TypeSpecifier typeSpecifiers | e
    // typeSpecifier -> 'void' | 'int' | 'float' | 'double' | 'char' | 'string' | 'signed' | 'unsigned'
    public ArrayList<ASTToken> typeSpecifiers() {
        ArrayList<ASTToken> tl = new ArrayList<ASTToken>();

        nextToken = tknList.get(tokenIndex);
        while (isTypeSpecifier(nextToken.type)) {
            ASTToken tkn = new ASTToken(nextToken.lexme.replace("'", ""), tokenIndex);
            tokenIndex++;
            tl.add(tkn);
            nextToken = tknList.get(tokenIndex);
        }
        return tl;
    }

    // arguments -> argumentList | e
    public ArrayList<ASTParamsDeclarator> arguments() {
        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("')'")) {
            return null;
        } else {
            ArrayList<ASTParamsDeclarator> pd = argumentList();
            return pd;
        }
    }

    // argumentList -> arguement ',' argumentList | argument
    // argument ->typeSpecifiers declarator
    public ArrayList<ASTParamsDeclarator> argumentList() {
        ArrayList<ASTParamsDeclarator> pdl = new ArrayList<ASTParamsDeclarator>();

        ASTParamsDeclarator pd1 = new ASTParamsDeclarator();
        ArrayList<ASTToken> tl1 = typeSpecifiers();
        pd1.specfiers = tl1;
        pd1.children.addAll(tl1);

        ASTVariableDeclarator vd1 = new ASTVariableDeclarator();
        ASTIdentifier id1 = new ASTIdentifier();
        nextToken = tknList.get(tokenIndex);
        id1.tokenId = tokenIndex;
        id1.value = nextToken.lexme.replace("'", "");
        matchToken("Identifier");
        vd1.identifier = id1;
        vd1.children.add(id1);
        pd1.declarator = vd1;
        pd1.children.add(vd1);
        pdl.add(pd1);

        nextToken = tknList.get(tokenIndex);
        while (nextToken.type.equals("','")) {
            matchToken("','");

            ASTParamsDeclarator pd2 = new ASTParamsDeclarator();
            ArrayList<ASTToken> tl2 = typeSpecifiers();
            pd2.specfiers = tl2;
            pd2.children.addAll(tl2);

            ASTVariableDeclarator vd2 = new ASTVariableDeclarator();
            ASTIdentifier id2 = new ASTIdentifier();
            nextToken = tknList.get(tokenIndex);
            id2.tokenId = tokenIndex;
            id2.value = nextToken.lexme.replace("'", "");
            matchToken("Identifier");
            vd2.identifier = id2;
            vd2.children.add(id2);
            pd2.declarator = vd2;
            pd2.children.add(vd2);
            pdl.add(pd2);

            nextToken = tknList.get(tokenIndex);
        }
        return pdl;
    }

    // CompoundStatement -> '{' blockItemList '}'
    public ASTCompoundStatement compoundStatement() {
        matchToken("'{'");

        ASTCompoundStatement cs = new ASTCompoundStatement();
        ArrayList<ASTNode> nl = blockItemList();
        cs.blockItems = nl;
        if (nl != null) {
            cs.children.addAll(nl);
        }

        matchToken("'}'");

        return cs;
    }

    // blockItemList -> declaration blockItemList | statement blockItemList | e
    public ArrayList<ASTNode> blockItemList() {
        ArrayList<ASTNode> bil1 = new ArrayList<ASTNode>();
        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("'}'")) {
            return null;
        } else {
            if (isTypeSpecifier(nextToken.type)) {
                ASTDeclaration decl = declaration();
                bil1.add(decl);
            } else {
                ASTStatement stat = statement();
                bil1.add(stat);
            }
            ArrayList<ASTNode> bil2 = blockItemList();
            if (bil2 != null) {
                bil1.addAll(bil2);
            }
            return bil1;
        }
    }

    // declaration -> typeSpecifiers initDeclaratorList ';'
    public ASTDeclaration declaration() {
        ASTDeclaration decl = new ASTDeclaration();
        ArrayList<ASTToken> tl = typeSpecifiers();
        decl.specifiers = tl;
        decl.children.addAll(tl);

        ArrayList<ASTInitList> il = initDeclaratorList();
        decl.initLists = il;
        decl.children.addAll(il);

        matchToken("';'");

        return decl;
    }

    // initDeclaratorList -> initDeclarator | initDeclarator ',' initDeclaratorList
    public ArrayList<ASTInitList> initDeclaratorList() {
        ArrayList<ASTInitList> idl1 = new ArrayList<ASTInitList>();
        ASTInitList idecl = initDeclarator();
        idl1.add(idecl);

        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("','")) {
            matchToken("','");
            ArrayList<ASTInitList> idl2 = initDeclaratorList();
            idl1.addAll(idl2);
        }

        return idl1;
    }

    // initDeclarator -> declarator | declarator '=' initializer
    public ASTInitList initDeclarator() {
        ASTInitList idecl = new ASTInitList();
        ASTDeclarator declarator = declarator();
        idecl.declarator = declarator;
        idecl.children.add(declarator);

        ArrayList<ASTExpression> exprs = null;
        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("'='")) {
            matchToken("'='");
            exprs = initializer();
            if (exprs != null) {
                idecl.exprs = exprs;
            }
            idecl.children.addAll(exprs);
        }
        return idecl;
    }

    // declarator -> identifier postDeclarator
    public ASTDeclarator declarator() {
        ASTVariableDeclarator vd = new ASTVariableDeclarator();
        ASTIdentifier identifier = new ASTIdentifier();
        nextToken = tknList.get(tokenIndex);
        identifier.tokenId = tokenIndex;
        identifier.value = nextToken.lexme.replace("'", "");
        matchToken("Identifier");
        vd.identifier = identifier;
        vd.children.add(identifier);

        return postDeclarator(vd);
    }

    // postDeclarator -> '[' assignmentExpression ']' postDeclarator | '[' ']' postDeclarator | e
    public ASTDeclarator postDeclarator(ASTDeclarator vd) {
        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("'['")) {
            matchToken("'['");
            ASTArrayDeclarator ad = new ASTArrayDeclarator();
            ad.declarator = vd;
            ad.children.add(vd);
            nextToken = tknList.get(tokenIndex);
            if (!nextToken.type.equals("']'")) {
                ASTExpression expr = assignmentExpression();
                ad.expr = expr;
                ad.children.add(expr);
            }
            matchToken("']'");
            return postDeclarator(ad);
        } else {
            return vd;
        }
    }

    // initializer -> assignmentExpression | '{' expression '}'
    public ArrayList<ASTExpression> initializer() {
        ArrayList<ASTExpression> initl = new ArrayList<ASTExpression>();
        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("'{'")) {
            matchToken("'{'");
            ArrayList<ASTExpression> el = expression();
            initl.addAll(el);
            matchToken("'}'");
        } else {
            ASTExpression ae = assignmentExpression();
            initl.add(ae);
        }
        return initl;
    }

    // statement -> breakStatement | continueStatement | gotoStatement | returnStatement |
    //              compoundStatement | selectionStatement | iterationStatement | expressionStatement
    public ASTStatement statement() {
        nextToken = tknList.get(tokenIndex);
        return switch (nextToken.type) {
            case "'break'" -> breakStatement();
            case "'continue'" -> continueStatement();
            case "'goto'" -> gotoStatement();
            case "'return'" -> returnStatement();
            case "'{'" -> compoundStatement();
            case "'if'" -> selectionStatement();
            case "'for'" -> iterationStatement();
            default -> expressionStatement();
        };
    }

    // breakStatement -> 'break' ';'
    public ASTBreakStatement breakStatement() {
        ASTBreakStatement bs = new ASTBreakStatement();
        matchToken("'break'");
        matchToken("';'");
        return bs;
    }

    // continueStatement -> 'continue' ';'
    public ASTContinueStatement continueStatement() {
        ASTContinueStatement cs = new ASTContinueStatement();
        matchToken("'continue'");
        matchToken("';'");
        return cs;
    }

    // gotoStatement -> 'goto' identifier ';'
    public ASTGotoStatement gotoStatement() {
        ASTGotoStatement gs = new ASTGotoStatement();
        matchToken("'goto'");

        nextToken = tknList.get(tokenIndex);
        ASTIdentifier label = new ASTIdentifier();
        label.tokenId = tokenIndex;
        label.value = nextToken.lexme.replace("'", "");
        matchToken("Identifier");
        gs.label = label;
        gs.children.add(label);

        matchToken("';'");

        return gs;
    }

    // returnStatement -> 'return' expression ';' | 'return' ';'
    public ASTReturnStatement returnStatement() {
        ASTReturnStatement rs = new ASTReturnStatement();
        matchToken("'return'");
        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("';'")) {
            rs.expr = null;
        } else {
            ASTExpression expr = assignmentExpression();
            rs.expr.add(expr);
            rs.children.add(expr);
        }
        matchToken("';'");
        return rs;
    }

    // selectionStatement -> 'if' '(' expression ')' statement | 'if' '(' expression ')' statement 'else' statement
    public ASTSelectionStatement selectionStatement() {
        ASTSelectionStatement ss = new ASTSelectionStatement();
        matchToken("'if'");
        matchToken("'('");

        LinkedList<ASTExpression> cond = new LinkedList<ASTExpression>(expression());
        ss.cond = cond;
        ss.children.addAll(cond);

        matchToken("')'");

        ASTStatement then = statement();
        ss.then = then;
        ss.children.add(then);

        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("'else'")) {
            matchToken("'else'");
            ASTStatement otherwise = statement();
            ss.otherwise = otherwise;
            ss.children.add(otherwise);
        }

        return ss;
    }

    // iterationStatement -> 'for' '(' expression ';' expression ';' expression ')' statement  | 'for' '(' expression ';' expression ';' ')' statement
    // iterationStatement -> 'for' '(' declaration ';' expression ';' expression ')' statement  | 'for' '(' declaration ';' expression ';' ')' statement
    public ASTStatement iterationStatement() {
        matchToken("'for'");
        matchToken("'('");

        nextToken = tknList.get(tokenIndex);
        if (isTypeSpecifier(nextToken.type)) {
            ASTIterationDeclaredStatement ids = new ASTIterationDeclaredStatement();
            ASTDeclaration init = declaration();
            LinkedList<ASTExpression> cond = null;
            LinkedList<ASTExpression> step = null;
            nextToken = tknList.get(tokenIndex);
            if (!nextToken.type.equals(";")) {
                cond = new LinkedList<ASTExpression>(expression());
            }
            matchToken("';'");
            nextToken = tknList.get(tokenIndex);
            if (!nextToken.type.equals("')'")) {
                step = new LinkedList<ASTExpression>(expression());
            }
            matchToken("')'");

            ids.init = init;
            ids.children.add(init);
            ids.cond = cond;
            ids.children.addAll(cond);
            ids.step = step;
            ids.children.addAll(step);

            ASTStatement stat = statement();
            ids.stat = stat;
            ids.children.add(stat);

            return ids;
        } else {
            ASTIterationStatement is = new ASTIterationStatement();
            LinkedList<ASTExpression> init = null;
            LinkedList<ASTExpression> cond = null;
            LinkedList<ASTExpression> step = null;
            nextToken = tknList.get(tokenIndex);
            if (!nextToken.type.equals("';'")) {
                init = new LinkedList<ASTExpression>(expression());
            }
            matchToken("';'");
            nextToken = tknList.get(tokenIndex);
            if (!nextToken.type.equals("';'")) {
                cond = new LinkedList<ASTExpression>(expression());
            }
            matchToken("';'");
            nextToken = tknList.get(tokenIndex);
            if (!nextToken.type.equals("')'")) {
                step = new LinkedList<ASTExpression>(expression());
            }
            matchToken("')'");

            is.init = init;
            is.children.addAll(init);
            is.cond = cond;
            is.children.addAll(cond);
            is.step = step;
            is.children.addAll(step);

            ASTStatement stat = statement();
            is.stat = stat;
            is.children.add(stat);

            return is;
        }
    }

    // expressionStatement -> expression ';' | ';'
    public ASTExpressionStatement expressionStatement() {
        ASTExpressionStatement es = new ASTExpressionStatement();
        nextToken = tknList.get(tokenIndex);
        if (!nextToken.type.equals("';'")) {
            ArrayList<ASTExpression> exprs = expression();
            es.exprs = exprs;
            es.children.addAll(exprs);
        }
        matchToken("';'");
        return es;
    }

    // expression -> assignmentExpression ',' expression | assignmentExpression
    public ArrayList<ASTExpression> expression() {
        ArrayList<ASTExpression> expr1 = new ArrayList<ASTExpression>();
        ASTExpression ae1 = assignmentExpression();
        expr1.add(ae1);

        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("','")) {
            matchToken("','");
            ArrayList<ASTExpression> expr2 = expression();
            expr1.addAll(expr2);
        }
        return expr1;
    }

    // assignmentExpression -> conditionalExpression | unaryExpression assignmentOperator assignmentExpression
    // assignmentOperator -> '=' | '*=' | '/=' | '%=' | '+=' | '-='
    public ASTExpression assignmentExpression() {
        int pos = 0;
        int cntParenthesis = 0;
        int cntBracket = 0;
        int cntBrace = 0;

        ScannerToken tkn = tknList.get(tokenIndex + pos);
        while (!tkn.type.equals("','") && !tkn.type.equals("';'")) {    // 不停向后扫描，检查是否有赋值运算符
            if (isAssignmentOperator(tkn.type)) {
                ASTBinaryExpression be = new ASTBinaryExpression();

                ASTExpression expr1 = unaryExpression();
                be.expr1 = expr1;
                be.children.add(expr1);

                nextToken = tknList.get(tokenIndex);
                ASTToken op = new ASTToken();
                if (isAssignmentOperator(nextToken.type)) {
                    op.value = nextToken.lexme.replace("'", "");
                    op.tokenId = tokenIndex;
                    tokenIndex++;
                }
                be.op = op;
                be.children.add(op);

                ASTExpression expr2 = assignmentExpression();
                be.expr2 = expr2;
                be.children.add(expr2);

                return be;
            } else if (tkn.type.equals("'('")) {
                cntParenthesis++;
            } else if (tkn.type.equals("')'")) {
                cntParenthesis--;
            } else if (tkn.type.equals("'['")) {
                cntBracket++;
            } else if (tkn.type.equals("']'")) {
                cntBracket--;
            } else if (tkn.type.equals("'{'")) {
                cntBrace++;
            } else if (tkn.type.equals("'}'")) {
                cntBrace--;
            }
            if (cntParenthesis < 0 || cntBrace < 0 || cntBracket < 0) {
                break;
            }
            pos++;
            if (tokenIndex + pos >= tknList.size()) {
                break;
            }
            tkn = tknList.get(tokenIndex + pos);
        }
        return conditionalExpression();
    }

    // conditionalExpression -> logicalOrExpression | logicalOrExpression '?' expression ':' conditionalExpression
    public ASTExpression conditionalExpression() {
        ASTConditionExpression ce = new ASTConditionExpression();
        ASTExpression condExpr = logicalOrExpression();

        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("'?'")) {
            matchToken("'?'");

            ce.condExpr = condExpr;
            ce.children.add(condExpr);

            LinkedList<ASTExpression> trueExpr = new LinkedList<ASTExpression>(expression());
            ce.trueExpr = trueExpr;
            ce.children.addAll(trueExpr);

            matchToken("':'");

            ASTExpression falseExpr = conditionalExpression();
            ce.falseExpr = falseExpr;
            ce.children.add(falseExpr);
            return ce;
        }
        return condExpr;
    }

    // logicalOrExpression -> logicalAndExpression | logicalAndExpression '||' logicalOrExpression
    public ASTExpression logicalOrExpression() {
        ASTBinaryExpression loe = new ASTBinaryExpression();
        ASTExpression expr1 = logicalAndExpression();

        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("'||'")) {
            loe.expr1 = expr1;
            loe.children.add(expr1);

            ASTToken op = new ASTToken(nextToken.lexme.replace("'", ""), tokenIndex);
            tokenIndex++;

            ASTExpression expr2 = logicalOrExpression();
            loe.expr2 = expr2;
            loe.children.add(expr2);

            loe.op = op;
            loe.children.add(op);

            return loe;
        }
        return expr1;
    }

    // logicalAndExpression -> equalityExpression | equalityExpression '&&' logicalAndExpression
    public ASTExpression logicalAndExpression() {
        ASTBinaryExpression lae = new ASTBinaryExpression();
        ASTExpression expr1 = equalityExpression();

        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("'&&'")) {
            lae.expr1 = expr1;
            lae.children.add(expr1);

            ASTToken op = new ASTToken(nextToken.lexme.replace("'", ""), tokenIndex);
            tokenIndex++;

            ASTExpression expr2 = logicalAndExpression();
            lae.expr2 = expr2;
            lae.children.add(expr2);

            lae.op = op;
            lae.children.add(op);

            return lae;
        }
        return expr1;
    }

    // equalityExpression -> relationalExpression | relationalExpression '==' equalityExpression | relationalExpression '!=' equalityExpression
    public ASTExpression equalityExpression() {
        ASTBinaryExpression ee = new ASTBinaryExpression();
        ASTExpression expr1 = relationalExpression();

        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("'=='") || nextToken.type.equals("'!='")) {
            ee.expr1 = expr1;
            ee.children.add(expr1);

            ASTToken op = new ASTToken(nextToken.lexme.replace("'", ""), tokenIndex);
            tokenIndex++;

            ASTExpression expr2 = equalityExpression();
            ee.expr2 = expr2;
            ee.children.add(expr2);

            ee.op = op;
            ee.children.add(op);

            return ee;
        }
        return expr1;
    }

    // relationalExpression -> additiveExpression |
    //                          additiveExpression '<' relationalExpression |
    //                          additiveExpression '>' relationalExpression |
    //                          additiveExpression '<=' relationalExpression |
    //                          additiveExpression '>=' relationalExpression
    public ASTExpression relationalExpression() {
        ASTBinaryExpression re = new ASTBinaryExpression();
        ASTExpression expr1 = additiveExpression();

        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("'<'") || nextToken.type.equals("'>'") || nextToken.type.equals("'<='") || nextToken.type.equals("'>='")) {
            re.expr1 = expr1;
            re.children.add(expr1);

            ASTToken op = new ASTToken(nextToken.lexme.replace("'", ""), tokenIndex);
            tokenIndex++;

            ASTExpression expr2 = relationalExpression();
            re.expr2 = expr2;
            re.children.add(expr2);

            re.op = op;
            re.children.add(op);

            return re;
        }
        return expr1;
    }

    // additiveExpression -> multiplicativeExpression | multiplicativeExpression '+' additiveExpression | multiplicativeExpression '-' additiveExpression
    public ASTExpression additiveExpression() {
        ASTBinaryExpression ae = new ASTBinaryExpression();
        ASTExpression expr1 = multiplicativeExpression();

        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("'+'") || nextToken.type.equals("'-'")) {
            ae.expr1 = expr1;
            ae.children.add(expr1);

            ASTToken op = new ASTToken(nextToken.lexme.replace("'", ""), tokenIndex);
            tokenIndex++;

            ASTExpression expr2 = additiveExpression();
            ae.expr2 = expr2;
            ae.children.add(expr2);

            ae.op = op;
            ae.children.add(op);

            return ae;
        }
        return expr1;
    }

    // multiplicativeExpression -> castExpression | castExpression '*' multiplicativeExpression |  castExpression '/' multiplicativeExpression | castExpression '%' multiplicativeExpression
    public ASTExpression multiplicativeExpression() {
        ASTBinaryExpression me = new ASTBinaryExpression();
        ASTExpression expr1 = castExpression();

        nextToken = tknList.get(tokenIndex);
        if (nextToken.type.equals("'*'") || nextToken.type.equals("'/'") || nextToken.type.equals("'%'")) {
            me.expr1 = expr1;
            me.children.add(expr1);

            ASTToken op = new ASTToken(nextToken.lexme.replace("'", ""), tokenIndex);
            tokenIndex++;

            ASTExpression expr2 = multiplicativeExpression();
            me.expr2 = expr2;
            me.children.add(expr2);

            me.op = op;
            me.children.add(op);

            return me;
        }
        return expr1;
    }

    // castExpression -> unaryExpression | '(' typeSpecifiers ')' castExpression
    public ASTExpression castExpression() {
        ASTCastExpression ce1 = new ASTCastExpression();
        nextToken = tknList.get(tokenIndex);
        ScannerToken nntoken = tknList.get(tokenIndex + 1);
        if (nextToken.type.equals("'('") && isTypeSpecifier(nntoken.type)) {
            matchToken("'('");

            ASTTypename typename = new ASTTypename();
            ArrayList<ASTToken> tkn = typeSpecifiers();
            typename.specfiers = tkn;
            typename.children.addAll(tkn);
            ce1.typename = typename;
            ce1.children.add(typename);

            matchToken("')'");

            ASTExpression ce2 = castExpression();

            ce1.expr = ce2;
            ce1.children.add(ce2);

            return ce1;

        } else {
            return unaryExpression();
        }
    }

    // unaryExpression -> postfixExpression | unaryOperator unaryExpression
    // unaryOperator -> '++' | '--' | '&' | '*' | '+' | '-' | '!' | 'sizeof'
    public ASTExpression unaryExpression() {
        ASTUnaryExpression ue = new ASTUnaryExpression();
        nextToken = tknList.get(tokenIndex);
        if (isUnaryOperator(nextToken.type)) {
            ASTToken op = new ASTToken(nextToken.lexme.replace("'", ""), tokenIndex);
            tokenIndex++;
            ue.op = op;
            ue.children.add(op);

            ASTExpression expr = unaryExpression();
            ue.expr = expr;
            ue.children.add(expr);

            return ue;
        } else {
            return postfixExpression();
        }
    }

    // postfixExpression -> primaryExpression postfixExpressionPost
    public ASTExpression postfixExpression() {
        ASTExpression primExpr = primaryExpression();
        return postfixExpressionPost(primExpr);
    }

    // postfixExpressionPost -> '[' expression ']' postfixExpressionPost |
    //                          '(' expression ')' postfixExpressionPost |
    //                          '(' ')' postfixExpressionPost |
    //                          '.' postfixExpressionPost |
    //                          '->' postfixExpressionPost |
    //                          '++' postfixExpressionPost |
    //                          '--' postfixExpressionPost |
    //                          e
    public ASTExpression postfixExpressionPost(ASTExpression preNode) {
        nextToken = tknList.get(tokenIndex);
        switch (nextToken.type) {
            case "'['" -> {     // ASTArrayAccess
                ASTArrayAccess aa = new ASTArrayAccess();
                aa.arrayName = preNode;
                aa.children.add(preNode);

                matchToken("'['");

                List<ASTExpression> elements = new ArrayList<ASTExpression>(expression());
                aa.elements = elements;
                aa.children.addAll(elements);

                matchToken("']'");

                return postfixExpressionPost(aa);
            }
            case "'('" -> {     // ASTFunctionCall
                ASTFunctionCall fc = new ASTFunctionCall();
                fc.funcname = preNode;
                fc.children.add(preNode);

                matchToken("'('");

                List<ASTExpression> argList = new ArrayList<ASTExpression>();
                nextToken = tknList.get(tokenIndex);
                if (!nextToken.type.equals("')'")) {
                    argList = expression();
                }
                fc.argList = argList;
                if (argList != null) {
                    fc.children.addAll(argList);
                }

                matchToken("')'");

                return postfixExpressionPost(fc);
            }
            case "'.'", "'->'" -> {    // ASTMemberAccess
                ASTMemberAccess ma = new ASTMemberAccess();

                ASTToken op = new ASTToken(nextToken.lexme.replace("'", ""), tokenIndex);
                tokenIndex++;
                ma.op = op;
                ma.children.add(op);

                ma.master = preNode;
                ma.children.add(preNode);

                nextToken = tknList.get(tokenIndex);
                ASTIdentifier member = new ASTIdentifier(nextToken.lexme.replace("'", ""), tokenIndex);
                matchToken("Identifier");
                ma.member = member;
                ma.children.add(member);

                return postfixExpressionPost(ma);
            }
            case "'++'", "'--'" -> {
                ASTPostfixExpression pe = new ASTPostfixExpression();
                pe.expr = preNode;
                pe.children.add(preNode);

                ASTToken op = new ASTToken(nextToken.lexme.replace("'", ""), tokenIndex);
                tokenIndex++;
                pe.op = op;
                pe.children.add(op);
                return postfixExpressionPost(pe);
            }
            default -> {
                return preNode;
            }
        }
    }

    // primaryExpression -> Identifier | IntegerConstant | FloatingConstant |
    //                      CharacterConstant | StringLiteral | '(' expression ')'
    public ASTExpression primaryExpression() {
        nextToken = tknList.get(tokenIndex);
        switch (nextToken.type) {
            case "Identifier":
                ASTIdentifier id = new ASTIdentifier(nextToken.lexme.replace("'", ""), tokenIndex);
                matchToken("Identifier");
                return id;
            case "IntegerConstant":
                ASTIntegerConstant ic = new ASTIntegerConstant(Integer.parseInt(nextToken.lexme.replace("'", "")), tokenIndex);
                matchToken("IntegerConstant");
                return ic;
            case "FloatConstant":
                ASTFloatConstant fc = new ASTFloatConstant(Double.parseDouble(nextToken.lexme.replace("'", "")), tokenIndex);
                matchToken("FloatConstant");
                return fc;
            case "CharacterConstant":
                ASTCharConstant cc = new ASTCharConstant(nextToken.lexme.replace("'", ""), tokenIndex);
                matchToken("CharacterConstant");
                return cc;
            case "StringLiteral":
                ASTStringConstant sc = new ASTStringConstant(nextToken.lexme.replace("'", ""), tokenIndex);
                matchToken("StringLiteral");
                return sc;
            case "'('":
                matchToken("'('");
                ASTExpression expr = assignmentExpression();
                matchToken("')'");
                return expr;
            default:
                return null;
        }
    }
}














