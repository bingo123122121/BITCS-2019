#ifndef AnswerJudge_H
#define AnswerJudge_H

#include "ProblemExtraction.h"
//#include <QString>

class AnswerJudge
{
public:
	AnswerJudge();
	~AnswerJudge();
	void readAnswer(ProblemExtraction* pe);
    bool judgeAnswer(Number& answer, int i);
    string getAnswer(int i);

private:
	//ProblemExtraction* pe;
	Number* standardAnswer;
	int num;
};

#endif
