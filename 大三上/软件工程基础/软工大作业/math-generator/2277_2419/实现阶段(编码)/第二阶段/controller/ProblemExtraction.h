// 题目抽取模块
#ifndef ProblemExtraction_H
#define ProblemExtraction_H

#include "../model/File.h"
#include "../model/Number.h"

class ProblemExtraction
{
public:
    ProblemExtraction();
    ~ProblemExtraction();
    void extract(int num = 2);
    string getProblem(int i);
    string getAnswer();
    int getNum();

private:
    File* file;
    string problems[1000];
    string answers;
    int num;
};

#endif
