package bit.minisys.minicc.icgen;

import bit.minisys.minicc.parser.ast.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExampleICPrinter {
    private List<Quat> quats;

    public ExampleICPrinter(List<Quat> quats) {
        this.quats = quats;
    }

    public void print(String filename) {
        StringBuilder sb = new StringBuilder();
        for (Quat quat : quats) {
            String op = quat.getOp();
            String res = astStr(quat.getRes());
            String opnd1 = astStr(quat.getOpnd1());
            String opnd2 = astStr(quat.getOpnd2());
            sb.append("(" + op + "," + opnd1 + "," + opnd2 + "," + res + ")\n");
        }
        // write
        try {
            FileWriter fileWriter = new FileWriter(new File(filename));
            fileWriter.write(sb.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String astStr(ASTNode node) {
        if (node == null) {
            return "";
        } else if (node instanceof ASTIdentifier) {
            return ((ASTIdentifier) node).value;
        } else if (node instanceof ASTIntegerConstant) {
            return ((ASTIntegerConstant) node).value + "";
        } else if (node instanceof ASTFloatConstant) {
            return ((ASTFloatConstant) node).value + "";
        } else if (node instanceof ASTCharConstant) {
            return ((ASTCharConstant) node).value;
        } else if (node instanceof ASTStringConstant) {
            return ((ASTStringConstant) node).value;
        } else if (node instanceof TemporaryValue) {
            return ((TemporaryValue) node).name();
        } else if (node instanceof DescriptionLabel) {
            return ((DescriptionLabel) node).name;
        } else if (node instanceof ASTVariableDeclarator) {
            return ((ASTVariableDeclarator) node).getName();
        } else if (node instanceof ASTFunctionDeclarator) {
            return ((ASTFunctionDeclarator) node).getName();
        } else if (node instanceof ASTToken) {
            return switch (((ASTToken) node).value) {
                case "int" -> "int";
                case "void" -> "void";
                case "float" -> "float";
                case "char" -> "char";
                default -> "";
            };
        } else {
            return "";
        }
    }
}
