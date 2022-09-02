#include "Cmd.h"

Cmd::Cmd()
{
	pg = new ProblemGenerate();
	pe = new ProblemExtraction();
	aj = new AnswerJudge();
	
	num = 0;
	correct = 0;
}

Cmd::~Cmd()
{
	delete pg;
	delete pe;
	delete aj;
}

void Cmd::clear()
{
	num = 0;
	argv[0] = argv[1] = "";

}

int Cmd::parse(string cmd)
{
	int i = 0, flag = 0;
	clear();
	while (i < cmd.length())
	{
		int temp = 0, flag = 0;
		while (i < cmd.length() && cmd[i] != ' ')
		{
			argv[num] += cmd[i];
			i += 1;
			flag = 1;
		}
		while (i < cmd.length() && cmd[i] == ' ')
		{
			i += 1;
		}
		if (flag)
		{
			num += 1;
		}
		if (num == 3)
		{
			cout << "参数过多！" << endl;
			return 4;
		}
	}

	//cout << "argv:";
	//for (int i = 0; i < num; i++)
	//{
	//	cout << argv[i] << " ";
	//}
	//cout << endl;

	return func(this->num, this->argv);
}

bool Cmd::isNum(string str)
{
	if (str.length() == 0)
	{
		cout << "缺少参数！" << endl;
		return false;
	}
	for (int i = 0; i < str.length(); i++)
	{
		if (!isalnum(str[i]))
		{
			return false;
		}
	}
	return true;
}

int Cmd::func(int argc, string* argv)
{
	if (argv[0] == "-g")
	{
		if (argc == 1)
		{
			return problemGenerate();
		}
		else
		{
			cout << "参数过多！" << endl;
			return 1;
		}
	}
	else if (argv[0] == "-e" && isNum(argv[1]))
	{
		int num = atoi(argv[1].c_str());
		if (num < 1 || num > 1000)
		{
			cout << "请输入1~1000内的整数！" << endl;
			return 2;
		}
		return problemExtraction(num);
	}
	else if (argv[0] == "-s")
	{
		if (argc == 1)
		{
			return answer();
		}
		else
		{
			cout << "参数过多！" << endl;
			return 2;
		}
	}
	else if (argv[0] == "-q")
	{
		return -1;
	}
	else
	{
		cout << "参数错误" << endl;
		return 4;
	}
}

int Cmd::problemGenerate()
{
	try
	{
		pg->generate();
		return 0;
	}
	catch (exception& e)
	{
		cout << e.what() << endl;
		return 1;
	}
}

int Cmd::problemExtraction(int num)
{
	try
	{
		pe->extract(num);
		aj->readAnswer(pe);
		return 0;
	}
	catch (exception& e)
	{
		cout << e.what() << endl;
		return 2;
	}
}

int Cmd::answer()
{
	int num = pe->getNum();
	correct = 0;
	char ans[100];
	for (int i = 0; i < num; i++)
	{
		cout << "第" << i + 1 << "题：";
		cout << pe->getProblem(i) << endl;
		cout << "请输入您的答案：";

		cin.getline(ans, 100);
		fflush(stdout);
		
		if (string(ans) == "-q")
		{
			cout << "您本次共答题"<< i << "道，正确" << correct << "道，错误" << i - correct << "道。" << endl;
			return -1;
		}

		if (answerJudge(i, ans))
		{
			correct++;
			cout << "您的答案正确";
		}
		else
		{
			cout << "您的答案错误";
		}
		cout << "，正确答案是" << aj->getAnswer(i) << endl << endl;
	}
	cout << "您本次共答题" << num << "道，正确" << correct << "道，错误" << num - correct << "道。" << endl;
	return 0;
}

bool Cmd::answerJudge(int num, string ans)
{
	ll fz = 0, fm = 1;
	sscanf_s(ans.c_str(), "%lld/%lld", &fz, &fm);
	Number answer = Number(fz, fm);
	return aj->judgeAnswer(answer, num);
}