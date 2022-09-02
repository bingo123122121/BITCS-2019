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
	int parse(string cmd);//����ֵ��ʾ״̬��-1���˳���0��������1����Ŀ���ɳ���2����Ŀ��ȡ����3���жϴ𰸳���4����������


private:
	string argv[2];//����
	int num;//�������� ��ȡ����Ŀ����
	int correct;//��������

	ProblemGenerate* pg;
	ProblemExtraction* pe;
	AnswerJudge* aj;

	void clear();

	int func(int argc, string* argv);//����ֵ��ʾ�Ƿ����
	int problemGenerate();
	int problemExtraction(int num);
	int answer();
	bool answerJudge(int num, string answer);

	bool isNum(string str);
};