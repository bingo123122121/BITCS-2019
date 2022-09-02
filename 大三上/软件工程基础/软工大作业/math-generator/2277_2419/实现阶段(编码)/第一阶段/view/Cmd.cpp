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
			cout << "�������࣡" << endl;
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
		cout << "ȱ�ٲ�����" << endl;
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
			cout << "�������࣡" << endl;
			return 1;
		}
	}
	else if (argv[0] == "-e" && isNum(argv[1]))
	{
		int num = atoi(argv[1].c_str());
		if (num < 1 || num > 1000)
		{
			cout << "������1~1000�ڵ�������" << endl;
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
			cout << "�������࣡" << endl;
			return 2;
		}
	}
	else if (argv[0] == "-q")
	{
		return -1;
	}
	else
	{
		cout << "��������" << endl;
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
		cout << "��" << i + 1 << "�⣺";
		cout << pe->getProblem(i) << endl;
		cout << "���������Ĵ𰸣�";

		cin.getline(ans, 100);
		fflush(stdout);
		
		if (string(ans) == "-q")
		{
			cout << "�����ι�����"<< i << "������ȷ" << correct << "��������" << i - correct << "����" << endl;
			return -1;
		}

		if (answerJudge(i, ans))
		{
			correct++;
			cout << "���Ĵ���ȷ";
		}
		else
		{
			cout << "���Ĵ𰸴���";
		}
		cout << "����ȷ����" << aj->getAnswer(i) << endl << endl;
	}
	cout << "�����ι�����" << num << "������ȷ" << correct << "��������" << num - correct << "����" << endl;
	return 0;
}

bool Cmd::answerJudge(int num, string ans)
{
	ll fz = 0, fm = 1;
	sscanf_s(ans.c_str(), "%lld/%lld", &fz, &fm);
	Number answer = Number(fz, fm);
	return aj->judgeAnswer(answer, num);
}