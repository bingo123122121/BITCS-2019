import java.util.Scanner;
import java.io.*;

public class MatrixMultiply{
    public static void main(String[] args) throws IOException {

        // 计时
        long start = System.currentTimeMillis();

        // 输入模块
        File file_in = new File("data.txt");
        Scanner scanner = new Scanner(file_in);
        int n1 = scanner.nextInt();
        int[][] matrix1 = new int[n1][n1];
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < n1; j++) {
                matrix1[i][j] = scanner.nextInt();
            }
        }
        int n2 = scanner.nextInt();
        int[][] matrix2 = new int[n2][n2];
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                matrix2[i][j] = scanner.nextInt();
            }
        }
        scanner.close();

        // 计算模块
        int [][] matrix3 = new int[n1][n2];
        for(int i = 0; i < n1; i++){
            for(int j = 0; j < n2; j++){
                matrix3[i][j] = 0;
                for(int k = 0; k < n1; k++){
                    matrix3[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        // 输出模块
        File file_out = new File("result_java.txt");
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file_out));
        BufferedWriter bw = new BufferedWriter(osw);
        for (int i = 0; i < n1; i++) {
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < n2; j++) {
                sb.append(matrix3[i][j] + (j == n2 - 1 ? "\n" : " "));
            }
            bw.write(sb.toString());
        }
        bw.close();

        // 计时
        long end = System.currentTimeMillis();
        System.out.printf("Total time:%dms", end - start);
        return;
    }
}