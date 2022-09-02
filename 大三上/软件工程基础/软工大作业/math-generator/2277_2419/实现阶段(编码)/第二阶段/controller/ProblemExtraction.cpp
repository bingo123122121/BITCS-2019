#include "ProblemExtraction.h"

ProblemExtraction::ProblemExtraction()
{
    num = 0;
    file = new File();
}

ProblemExtraction::~ProblemExtraction()
{
    free(file);
}

// 抽取num个题目
// 格式为“题目#题目2#……”，每个题目格式为“表达式 分子 分母”
void ProblemExtraction::extract(int num)
{
    if (num < 1 || num > 1000)
    {
        cout << "请输入1~1000的整数！" << endl;
        return;
    }
    this->num = num;
    string content = file->readProblem(num);
    //cout << content << endl;
    int pre = 0, now = 0, cnt = 0;
    answers = "";
    while ((now = content.find('#', now) + 1) != 0)
    {
        //cout << now << endl;
        string problem = content.substr(pre, now - pre - 1);
        //cout << problem << endl;
        int pos = problem.find(' ');

        problems[cnt] = problem.substr(0, pos);

        //answers[cnt] = problem.substr(pos + 1);

        answers += problem.substr(pos + 1);
        //cout << problem.substr(pos + 1) << endl;
        answers += "#";

        pre = now;
        cnt += 1;
    }
    cout << "抽取完成。" << endl;
}

string ProblemExtraction::getProblem(int i)
{
    if (i < 0 || i> num - 1)
    {
        cout << "请输入0~999的数字！" << endl;
        return "";
    }
    return problems[i];
}

string ProblemExtraction::getAnswer()
{
    return answers;
}

int ProblemExtraction::getNum()
{
    return num;
}
