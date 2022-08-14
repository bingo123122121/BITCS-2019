package bit.minisys.minicc.semantic;

import bit.minisys.minicc.parser.ast.ASTCompilationUnit;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class MySemanticAnalyzer implements IMiniCCSemantic {

    @Override
    public String run(String iFile) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ASTCompilationUnit program = (ASTCompilationUnit) mapper.readValue(new File(iFile), ASTCompilationUnit.class);
        System.out.println("in Semantic");

        // 语义分析
        ErrorInfo errorInfo = new ErrorInfo();
        SymbolTable globalSymbolTable = new SymbolTable();
        VisitSymbolTable semanticAnalyzer = new VisitSymbolTable(globalSymbolTable, errorInfo);

        program.accept(semanticAnalyzer);

        // 输出错误内容
        errorInfo.outputError();

        System.out.println("4. SemanticAnalyse finished!");

        return null;
    }
}
