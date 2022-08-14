package bit.minisys.minicc.scanner;

import bit.minisys.minicc.MiniCCCfg;
import bit.minisys.minicc.scanner.Scanner;
import bit.minisys.minicc.internal.util.MiniCCUtil;
import org.python.core.util.StringUtil;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class MyCScanner {

    private int lIndex = 0;
    private int cIndex = 0;
    private int iTknNum = 0;
    private String strTokens = "";

    public MyCScanner(){}

    private String genToken(int num, String content, String type, int lIndex, int cIndex){
        String strToken = "";

        strToken += "[@" + num + "," + cIndex + ":" + (cIndex + content.length() - 1);
        strToken += "='" + content + "',<" + type + ">," + (lIndex + 1) + ":" + cIndex + "]\n";

        return strToken;
    }

    private void printToken(String token, String content) throws IOException {
        switch (token) {
            case "KEYWORD" -> {
                strTokens += genToken(this.iTknNum, content, "Keyword", this.lIndex, this.cIndex);
                this.iTknNum++;
            }
            case "IDENTIFIER" -> {
                strTokens += genToken(this.iTknNum, content, "Identifier", this.lIndex, this.cIndex);
                this.iTknNum++;
            }
            case "INTEGER" -> {
                strTokens += genToken(this.iTknNum, content, "Integer", this.lIndex, this.cIndex);
                this.iTknNum++;
            }
            case "FLOAT" -> {
                strTokens += genToken(this.iTknNum, content, "Float", this.lIndex, this.cIndex);
                this.iTknNum++;
            }
            case "CHAR" -> {
                strTokens += genToken(this.iTknNum, content, "Char", this.lIndex, this.cIndex);
                this.iTknNum++;
            }
            case "STRING" -> {
                strTokens += genToken(this.iTknNum, content, "String", this.lIndex, this.cIndex);
                this.iTknNum++;
            }
            case "OPERATOR" -> {
                strTokens += genToken(this.iTknNum, content, "Operator", this.lIndex, this.cIndex);
                this.iTknNum++;
            }
            case "BOUND" -> {
                strTokens += genToken(this.iTknNum, content, "Bound", this.lIndex, this.cIndex);
                this.iTknNum++;
            }
            default -> {
                System.out.println("Error Input at line:" + lIndex + ", column:" + cIndex + ".");
            }
        }
    }

    public String run(String iFile) throws Exception{
        System.out.println("Start scanning...");

        ArrayList<String> srcLines = MiniCCUtil.readFile(iFile);
        StringBuilder source = new StringBuilder();
        for (String srcLine : srcLines) {
            source.append(srcLine).append("\n");
        }

        Reader reader = new StringReader(source.toString());
        Scanner scanner = new Scanner(reader);
        String content = "";
        String token = "";
        while(!scanner.yyatEOF()){

            token = scanner.yylex();
            content = scanner.yytext();

            if(Objects.equals(token, "BLANK") || Objects.equals(token, "ANNOTATION")){
                continue;
            }
            if(Objects.equals(token, "EOF")){
                break;
            }

            this.lIndex = scanner.getYyline();
            this.cIndex = scanner.getYycolumn();

            printToken(token, content);

        }

        String oFile = MiniCCUtil.removeAllExt(iFile) + MiniCCCfg.MINICC_SCANNER_OUTPUT_EXT;
        MiniCCUtil.createAndWriteFile(oFile, strTokens);

        System.out.println("Finish scanning!");
        return oFile;
    }
}
