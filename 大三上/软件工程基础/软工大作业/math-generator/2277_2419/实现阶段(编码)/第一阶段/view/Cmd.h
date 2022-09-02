#include <cstdio>
#include <cstring>
#include "../controller/ProblemGenerate.h"
#include "../controller/ProblemExtraction.h"
#include "../controller/AnswerJudge.h"

using namespace std;

class Cmd
{
public:
	Cmd();
	~Cmd();
	int parse(string cmd);//返回值表示状态：-1：退出，0：正常，1：题目生成出错，2：题目抽取出错，3：判断答案出错，4：其他错误


private:
	string argv[2];//参数
	int num;//参数个数 抽取的题目数量
	int correct;//正答数量

	ProblemGenerate* pg;
	ProblemExtraction* pe;
	AnswerJudge* aj;

	void clear();

	int func(int argc, string* argv);//返回值表示是否继续
	int problemGenerate();
	int problemExtraction(int num);
	int answer();
	bool answerJudge(int num, string answer);

	bool isNum(string str);
};