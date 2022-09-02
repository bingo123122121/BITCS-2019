#include "AnswerJudge.h"
#include <stdlib.h>
#pragma warning(disable:6385)

AnswerJudge::AnswerJudge()
{
	this->num = 0;
	//pe = NULL;
	standardAnswer = NULL;
}

AnswerJudge::~AnswerJudge()
{
	//if (pe != NULL) 
	//{
	//	delete pe;
	//}
	if (standardAnswer != NULL)
	{
		delete[] standardAnswer;
	}
}

void AnswerJudge::readAnswer(ProblemExtraction* pe)
{
	//this->pe = pe;
	string answers = pe->getAnswer();
    //cout << "an-------"<< answers << endl;

	this->num = pe->getNum();

	if (standardAnswer != NULL)
	{
		delete[] standardAnswer;
	}
	standardAnswer = new Number[pe->getNum()];

	int pre = 0, now = 0, cnt = 0;
	while ((now = answers.find('#', now) + 1) != 0)
	{
		string answer = answers.substr(pre, now - 1);
		int temp = answer.find(' ');
		ll nominator = atoll(answer.substr(0, temp).c_str());
		ll denominator = atoll(answer.substr(temp + 1).c_str());
		//cout << nominator << " " << denominator << endl;
		standardAnswer[cnt++].setNumber(nominator, denominator);
		pre = now;
	}
	return;
}

bool AnswerJudge::judgeAnswer(Number& answer, int i)
{
	//cout << "standard:" << standardAnswer[i].getNominator() << " " << standardAnswer[i].getDenominator() << endl;
	//cout << "answer:" << answer.getNominator() << " " << answer.getDenominator() << endl;
    return answer == standardAnswer[i] ? true : false;
}

string AnswerJudge::getAnswer(int i)
{
    //if (standardAnswer[i].getDenominator() == 1)
    //    return QString::number(standardAnswer[i].getNominator());
    //else
    //    return QString::number(standardAnswer[i].getNominator())+"/"+QString::number(standardAnswer[i].getDenominator());
	if (i < 0 || i>num)
	{
		cout << "ÇëÊäÈë0~numµÄÊý×Ö£¡" << endl;
		return "";
	}
	if (standardAnswer[i].getDenominator() == 1)
		return to_string(standardAnswer[i].getNominator());
	else
		return to_string(standardAnswer[i].getNominator()) + "/" + to_string(standardAnswer[i].getDenominator());
}
