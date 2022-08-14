#include <iostream>
#include <cstdio>
#include <fstream>
#include <ctime>

using namespace std;

int main(){
    // 计时
    clock_t start = clock();

    int n1, n2;
    int matrix1[305][305], matrix2[305][305], matrix3[305][305] = {0};
    fstream fp;

    // 输入模块
    fp.open("data.txt");
    fp >> n1;
    for (int i = 0; i < n1; i++){
        for (int j = 0; j < n1; j++){
            fp >> matrix1[i][j];
        }
    }
    fp >> n2;
    for (int i = 0; i < n2; i++){
        for (int j = 0; j < n2; j++){
            fp >> matrix2[i][j];
        }
    }

    // 计算模块
    for(int i = 0; i < n1; i++){
        for(int j = 0; j < n2; j++){
            for(int k = 0; k < n1; k++){
                matrix3[i][j] += matrix1[i][k] * matrix2[k][j];
            }
        }
    }

    // 输出模块
    fp.close();
    fp.open("result_C++.txt", ios::out);
    for(int i = 0; i < n1; i++){
        for(int j = 0; j < n2; j++){
            fp << matrix3[i][j] << (j == n2 - 1 ? '\n' : ' ');
        }
    }
    fp.close();

    // 计时
    clock_t end = clock();
    double endtime = (double)(end - start) / CLOCKS_PER_SEC;
    cout << "Total time:" << endtime * 1000 << "ms" << endl; // ms为单位

    return 0;
}