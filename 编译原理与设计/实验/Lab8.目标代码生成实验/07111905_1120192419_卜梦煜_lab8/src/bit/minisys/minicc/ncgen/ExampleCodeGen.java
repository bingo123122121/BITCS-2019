package bit.minisys.minicc.ncgen;

import bit.minisys.minicc.MiniCCCfg;
import bit.minisys.minicc.internal.util.MiniCCUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Stack;


public class ExampleCodeGen implements IMiniCCCodeGen {

    public LinkedList<QuatStr> quats;
    public int quatIdx;
    public int marsStrNum;
    public String jfType;
    public StringBuilder x86Code;

    public Stack<String> localStack;

    public Stack<String> argStack;


    public ExampleCodeGen() {
        quats = new LinkedList<>();
        quatIdx = 0;
        marsStrNum = 0;
        x86Code = new StringBuilder();
        localStack = new Stack<>();
        argStack = new Stack<>();
    }

    // 读入.ic.txt文件，保存为LinkedList<QuatStr>
    public void loadIC(String iFile) throws Exception {
        ArrayList<String> quatsStr = MiniCCUtil.readFile(iFile);
        for (String quatStr : quatsStr) {
            System.out.println(quatStr);
            quatStr = quatStr.substring(1, quatStr.length());
            String[] idx = quatStr.split(",");
            QuatStr quat = new QuatStr(idx[0], idx[3].substring(0, idx[3].length() - 1), idx[1], idx[2]);
            quats.add(quat);
        }
    }

    public QuatStr getCurQuat() {
        if (quatIdx < quats.size()) {
            return quats.get(quatIdx);
        } else {
            return null;
        }
    }

    public QuatStr getNextQuat() {
        if (quatIdx < quats.size() - 1) {
            quatIdx++;
            return quats.get(quatIdx);
        } else {
            return null;
        }
    }

    // include
    public void generateX86Include() {
        x86Code.append("""
                .386
                .model flat, stdcall
                option casemap:none
                include windows.inc
                include user32.inc
                includelib user32.lib
                include kernel32.inc
                includelib kernel32.lib
                include msvcrt.inc
                includelib msvcrt.lib
                includelib ucrt.lib
                scanf proto c :dword, :vararg
                printf proto c :dword, :vararg
                                
                """);
    }

    // .data
    public void generateX86Data() {
        x86Code.append("""
                .data
                digitPrintFmt db "%d ", 0
                digitGetFmt db "%d", 0
                """);

        // 遍历整个func，将所有Mars_PrintInt, Mars_PrintStr作为全局变量定义
        int tempIdx = 0;
        int marsCnt = 0;
        QuatStr quat1 = quats.get(tempIdx);
        while (tempIdx < quats.size()) {
            String op = quat1.getOp();
            String res = quat1.getRes();
            String opnd1 = quat1.getOpnd1();
            String opnd2 = quat1.getOpnd2();
            if (Objects.equals(res, "")) {
                quat1 = quats.get(++tempIdx);
                continue;
            }
            if (op.equals("arg") && opnd1.equals("Mars_PrintStr")) {
                if (res.equals("\"\\n\"")) {
                    x86Code.append("Mars_PrintStr" + marsCnt).append(" db ").append("0ah, 0\n");
                } else if (res.contains("\\n")) {
                    res = res.replace("\\n", "");
                    x86Code.append("Mars_PrintStr" + marsCnt).append(" db ").append(res).append(", 0ah, 0\n");
                } else {
                    x86Code.append("Mars_PrintStr" + marsCnt).append(" db ").append(res).append(", 0\n");
                }
                marsCnt++;
            }
            tempIdx++;
            if (tempIdx < quats.size()) {
                quat1 = quats.get(tempIdx);
            } else {
                break;
            }
        }
        x86Code.append("\n");
    }

    // 计算数组元素数量，目前未实现，对每个数组初始化大小为1000
    public int getArrSize(String str) {
        return 1000;
    }

    // 初始化函数参数
    public void initFunc() {
        localStack.clear();
        argStack.clear();
    }

    // 生成每个func
    public void generateX86Func() {

        String quatFuncName = getCurQuat().getOpnd2();
        x86Code.append(quatFuncName).append(" proc");

        // 添加param
        QuatStr quat = getNextQuat();
        while (quat.getOp().equals("param")) {
            x86Code.append(" ").append(quat.res).append(":dword");
            localStack.add(quat.res);
            quat = getNextQuat();
        }
        x86Code.append("\n");

        // 遍历整个func，将所有临时变量作为局部变量定义
        int tempIdx = quatIdx;
        int labelCnt = 0;
        QuatStr quat1 = quats.get(tempIdx);
        while (tempIdx < quats.size()) {
            System.out.println(tempIdx);
            String op = quat1.getOp();
            String res = quat1.getRes();
            String opnd1 = quat1.getOpnd1();
            String opnd2 = quat1.getOpnd2();

            if (op.equals("funcEnd")) {
                break;
            }

            if (Objects.equals(res, "")) {
                quat1 = quats.get(++tempIdx);
                continue;
            }

            if (op.equals("var") || (op.equals("arg") && !opnd1.equals("Mars_PrintStr")) || res.charAt(0) == '@') {
                if (localStack.search(res) == -1) {
                    x86Code.append("local ").append(res).append(":dword\n");
                    localStack.add(res);
                }
            } else if (op.equals("arr")) {
                if (localStack.search(res) == -1) {
                    int size = getArrSize(opnd1);
                    x86Code.append("local ").append(res + "[" + size + "]").append(":dword\n");
                    localStack.add(res + "[" + size + "]");
                }
            }

            tempIdx++;
            if (tempIdx < quats.size()) {
                quat1 = quats.get(tempIdx);
            } else {
                break;
            }

        }
        x86Code.append("\n");

        // 循环生成当前func所有语句的汇编代码
        while (!quat.getOp().equals("funcEnd")) {
            String op = quat.getOp();
            String res = quat.getRes();
            String opnd1 = quat.getOpnd1();
            String opnd2 = quat.getOpnd2();

            // 跳转类
            if (op.equals("label")) {
                x86Code.append(res).append(":\n");
            } else if (op.equals("jmp")) {
                x86Code.append("jmp ").append(res).append("\n");
            } else if (op.equals("jf")) {
                x86Code.append(jfType).append(" ").append(res).append("\n");
            } else if (op.equals("ret")) {
                if (!res.equals("")) {
                    x86Code.append("mov eax, ").append(res).append("\n");
                }
            }
            // 逻辑运算类
            else if (op.equals(">=") || op.equals("<=") || op.equals(">") ||
                    op.equals("<") || op.equals("==") || op.equals("!=")) {
                x86Code.append("mov eax, ").append(opnd1).append("\n");
                x86Code.append("cmp eax, ").append(opnd2).append("\n");
                switch (op) {
                    case ">=" -> jfType = "jl";
                    case "<=" -> jfType = "jg";
                    case ">" -> jfType = "jle";
                    case "<" -> jfType = "jge";
                    case "==" -> jfType = "jnz";
                    case "!=" -> jfType = "jz";
                }
            }
            // 算术运算类
            else if (op.equals("+") || op.equals("-") || op.equals("*") ||
                    op.equals("+=") || op.equals("-=") || op.equals("*=")) {
                x86Code.append("mov eax, ").append(opnd1).append("\n");
                switch (op) {
                    case "+" -> x86Code.append("add eax, ").append(opnd2).append("\n");
                    case "+=" -> x86Code.append("add eax, ").append(opnd2).append("\n");
                    case "-" -> x86Code.append("sub eax, ").append(opnd2).append("\n");
                    case "-=" -> x86Code.append("sub eax, ").append(opnd2).append("\n");
                    case "*" -> x86Code.append("imul eax, ").append(opnd2).append("\n");
                    case "*=" -> x86Code.append("imul eax, ").append(opnd2).append("\n");
                }
                x86Code.append("mov ").append(res).append(", eax\n");
            } else if (op.equals("/") || op.equals("%") || op.equals("/=") || op.equals("%=")) {
                x86Code.append("xor edx, edx\n");
                x86Code.append("mov eax, ").append(opnd1).append("\n");
                x86Code.append("mov ebx, ").append(opnd2).append("\n");
                x86Code.append("div ebx\n");
                if (op.equals("/") || op.equals("/=")) {
                    x86Code.append("mov ").append(res).append(", eax\n");
                } else {
                    x86Code.append("mov ").append(res).append(", edx\n");
                }
            }
            // 单目运算类
            else if (op.equals("++") || op.equals("--")) {
                x86Code.append("mov eax, ").append(res).append("\n");
                if (op.equals("++")) {
                    x86Code.append("inc eax\n");
                } else {
                    x86Code.append("dec eax\n");
                }
                x86Code.append("mov ").append(res).append(", eax\n");
            }
            // 赋值运算类
            else if (op.equals("=")) {
                x86Code.append("mov eax, ").append(opnd1).append("\n");
                x86Code.append("mov ").append(res).append(", eax\n");
            } else if (op.equals("=[]")) {
                x86Code.append("mov esi, ").append(opnd1).append("\n");
                x86Code.append("mov eax, ").append(opnd2).append("[esi*4]\n");
                x86Code.append("mov ").append(res).append(", eax\n");
            } else if (op.equals("[]=")) {
                x86Code.append("mov eax, ").append(opnd1).append("\n");
                x86Code.append("mov esi, ").append(opnd2).append("\n");
                x86Code.append("mov ").append(res).append("[4*esi], eax\n");
            }
            // 函数调用类
            else if (op.equals("arg") && !opnd1.equals("Mars_PrintStr")) {
                argStack.push(res);
            } else if (op.equals("call")) {
                if (opnd1.equals("Mars_PrintStr")) {
                    x86Code.append("invoke printf, addr Mars_PrintStr" + marsStrNum).append("\n");
                    marsStrNum++;
                } else if (opnd1.equals("Mars_PrintInt")) {
                    String src = argStack.pop();
                    x86Code.append("invoke printf, addr digitPrintFmt, ").append(src).append("\n");
                } else if (opnd1.equals("Mars_GetInt")) {
                    x86Code.append("invoke scanf, addr digitGetFmt, addr ").append(res).append("\n");
                } else {
                    x86Code.append("invoke ").append(opnd1);
                    while (!argStack.empty()) {
                        x86Code.append(", ").append(argStack.pop());
                    }
                    x86Code.append("\n");
                }

                if (!res.equals("") && !opnd1.equals("Mars_GetInt")) {
                    x86Code.append("mov ").append(res).append(", eax\n");
                }
            }

            quat = getNextQuat();
        }

        // func结束
        x86Code.append("ret\n");
        x86Code.append(quatFuncName).append(" endp\n");
        if (quatFuncName.equals("main")) {
            x86Code.append("end main\n");
        }
        x86Code.append("\n");
    }

    // .code
    public void generateX86Code() {
        x86Code.append(".code\n");
        QuatStr quatCur = getCurQuat();
        while (quatCur != null) {
            if (quatCur.getOp().equals("func")) {
                initFunc();
                generateX86Func();
            }
            quatCur = getNextQuat();
        }
    }

    public void generateX86ASM() throws Exception {
        generateX86Include();
        System.out.println("1");
        generateX86Data();
        System.out.println("2");
        generateX86Code();
        System.out.println("3");
    }

    @Override
    public String run(String iFile, MiniCCCfg cfg) throws Exception {
        String oFile = MiniCCUtil.remove2Ext(iFile) + MiniCCCfg.MINICC_CODEGEN_OUTPUT_EXT;

        if (!cfg.target.equals("x86")) {
            return oFile;
        }

        loadIC(iFile);

        generateX86ASM();

        System.out.println("7. Target code generation finished!");

        MiniCCUtil.createAndWriteFile(oFile, x86Code.toString());

        return oFile;
    }
}

// String版本的Quat，避免反复在ASTNode和String之间转换
class QuatStr {

    public String op;
    public String res;
    public String opnd1;
    public String opnd2;

    public QuatStr(String op, String res, String opnd1, String opnd2) {
        this.op = op;
        this.res = res;
        this.opnd1 = opnd1;
        this.opnd2 = opnd2;
    }

    public String getOp() {
        return op;
    }

    public String getRes() {
        return res;
    }

    public String getOpnd1() {
        return opnd1;
    }

    public String getOpnd2() {
        return opnd2;
    }
}











