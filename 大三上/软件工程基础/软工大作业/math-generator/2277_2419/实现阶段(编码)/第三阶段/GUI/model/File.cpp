#include "File.h"
#include <cstdio>


const string File::defaultProblemPath = "problem.txt";
const string File::defaultRecordPath = "record.txt";

File::File()
{
	problemPath = defaultProblemPath;
	recordPath = defaultRecordPath;

	// �ر�fstream
	if (!closeFile())
	{
		cout << "��ʼ���쳣"  << endl;
	}
}

File::~File()
{
	if (!closeFile())
	{
		cout << "�ر��쳣" << endl;
	}
}

bool File::openFile(string path)
{
	try
	{
		if (!fp.is_open())
		{
			fp.open(path, ios_base::in | ios_base::out | ios_base::app);
		}
	}
	catch (exception& e)
	{
		cout << e.what() << endl;
		return false;
	}
	return true;
}

bool File::closeFile()
{
	try
	{
		if (fp.is_open())
		{
			fp.close();
		}
	}
	catch (exception& e)
	{
		cout << e.what() << endl;
		return false;
	}
	return true;
}

void File::removeFile(string path)
{
	remove(path.c_str());
	fp.open(path);
	fp.close();
//    cout<<"remove ok"<<endl;
}

void File::setProblemPath(string path)
{
	problemPath = path;
}

void File::setRecordPath(string path)
{
	recordPath = path;
}

void File::generateRandom(int* ran, int num)
{
	srand((int)time(0));
	int temp, flag[1000] = { 0 };
	for (int i = 0; i < num; i++)
	{
		temp = rand()%1000;
		while (flag[temp])
		{
			temp = (rand() % 1000 + 1000) % 1000;
		}
		flag[temp] = 1;
		ran[i] = temp;
	}
	sort(ran, ran + num);
	return;
}

// ������ʽ�������ʽ�ʹ�һ��д���ļ�
// ��ʽΪ�����ʽ ���� ��ĸ��
void File::writeToFile(Formula* formula)
{
	// ���ļ�
	if (!openFile(problemPath))
	{
		cout << "����Ŀ�ļ��쳣" << endl;
		return;
	}

	// д����ʽ�ʹ�
	string temp = formula->getStr();
	Number ans = formula->getValue();
	ll nominator = ans.getNominator();
	ll denominator = ans.getDenominator();
	fp << temp << " " << to_string(nominator) << " " << to_string(denominator) << endl;
    //cout << temp << " " << to_string(nominator) << " " << to_string(denominator) << endl;

	// �ر��ļ�
	if (!closeFile())
	{
		cout << "�ر��ļ��쳣" << endl;
		return;
	}
}

void File::writeToFile(string record)
{
	// ���ļ�
	if (!openFile(recordPath))
	{
		cout << "�򿪼�¼�ļ��쳣" << endl;
		return;
	}

	fp << record << endl;

	// �ر��ļ�
	if (!closeFile())
	{
		cout << "�ر��ļ��쳣" << endl;
		return;
	}

}

// ����num����Ŀ������Ϊstring��������ʽ�á�#������
// ����100����
string File::readProblem(int num)
{
	if (!openFile(problemPath))
	{
		cout << "���ļ��쳣" << endl;
		return "";
	}
	if (num < 1 || num > 1000)
	{
		cout << "������1~1000��������" << endl;
		return "";
	}
	char temp[200] = {'\0'};
	string problems = "";    // �洢���ʽ
	int ran[1000];

	// ����ȥ����������������ran
	generateRandom(ran, num);

	int line = 0;
	for (int i = 0; i < num; i++) 
	{
		while (line != ran[i] + 1) 
		{
			fp.getline(temp, 200);
			line += 1;
		}
		problems += temp;
		problems += "#";
	}

	// �ر��ļ�
	if (!closeFile())
	{
		cout << "�ر��ļ��쳣" << endl;
		return "";
	}

	return problems;
}

string File::readRecord()
{
	if (!openFile(recordPath))
	{
		cout << "�򿪼�¼�ļ��쳣" << endl;
		return "";
	}
	string records = "";
	char record[100] = { '\0' };
	while (!fp.eof())
	{
		fp.getline(record, 1000);
		records += record;
		records += " ";
		fp.getline(record, 1000);
		records += record;
		records += "#";
	}
	if (!closeFile())
	{
		cout << "�رռ�¼�ļ��쳣" << endl;
		return "";
	}

	int l = records.length();
	cout << records.substr(0, l - 2) << endl;
	return records.substr(0, l - 2);
}

string File::getProblemPath()
{
	return problemPath;
}

string File::getRecordPath()
{
	return recordPath;
}