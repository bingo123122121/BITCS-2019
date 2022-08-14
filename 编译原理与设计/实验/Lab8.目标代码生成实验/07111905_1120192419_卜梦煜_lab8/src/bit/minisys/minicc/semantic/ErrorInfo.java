package bit.minisys.minicc.semantic;

import java.util.ArrayList;

public class ErrorInfo {
    public ArrayList<String> errors = new ArrayList<String>();

    // 变量使用前是否进行了定义
    public void addES01Identifier(String name) {
        String error = "ES01 > Identifier [" + name + "] is not defined";
        this.errors.add(error);
    }

    public void addES01FunctionCall(String functionName) {
        String error = "ES01 > FunctionCall [" + functionName + "] is not defined";
        this.errors.add(error);
    }

    // 变量是否存在重复定义
    public void addES02Declaration(String name) {
        String error = "ES02 > Declaration [" + name + "] is defined";
        this.errors.add(error);
    }

    public void addES02FunctionDefine(String functionName) {
        String error = "ES02 > Function [" + functionName + "] is defined";
        this.errors.add(error);
    }

    // break语句是否在循环语句中使用
    public void addES03() {
        String error = "ES03 > breakstatement must be in a loopstatement";
        this.errors.add(error);
    }

    public void outputError() {
        if (errors != null) {
            System.out.println("Error:");
            for (String error : this.errors) {
                System.out.println(error);
            }
        }
    }
}
