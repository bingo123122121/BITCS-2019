#include "File.h"
#include <cstdio>


const string File::defaultProblemPath = "problem.txt";
const string File::defaultRecordPath = "record.txt";

File::File()
{
	problemPath = defaultProblemPath;
	recordPath = defaultRecordPath;

	// 关闭fstream
	if (!closeFile())
	{
		cout << "初始化异常"  << endl;
	}
}

File::~File()
{
	if (!closeFile())
	{
		cout << "关闭异常" << endl;
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

// 输入表达式，将表达式和答案一起写入文件
// 格式为“表达式 分子 分母”
void File::writeToFile(Formula* formula)
{
	// 打开文件
	if (!openFile(problemPath))
	{
		cout << "打开题目文件异常" << endl;
		return;
	}

	// 写入表达式和答案
	string temp = formula->getStr();
	Number ans = formula->getValue();
	ll nominator = ans.getNominator();
	ll denominator = ans.getDenominator();
	fp << temp << " " << to_string(nominator) << " " << to_string(denominator) << endl;
    //cout << temp << " " << to_string(nominator) << " " << to_string(denominator) << endl;

	// 关闭文件
	if (!closeFile())
	{
		cout << "关闭文件异常" << endl;
		return;
	}
}

void File::writeToFile(string record)
{
	// 打开文件
	if (!openFile(recordPath))
	{
		cout << "打开记录文件异常" << endl;
		return;
	}

	fp << record << endl;

	// 关闭文件
	if (!closeFile())
	{
		cout << "关闭文件异常" << endl;
		return;
	}

}

// 读出num个题目，类型为string，多个表达式用“#”连接
// 至多100道题
string File::readProblem(int num)
{
	if (!openFile(problemPath))
	{
		cout << "打开文件异常" << endl;
		return "";
	}
	if (num < 1 || num > 1000)
	{
		cout << "请输入1~1000的整数！" << endl;
		return "";
	}
	char temp[200] = {'\0'};
	string problems = "";    // 存储表达式
	int ran[1000];

	// 产生去重有序的随机数序列ran
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

	// 关闭文件
	if (!closeFile())
	{
		cout << "关闭文件异常" << endl;
		return "";
	}

	return problems;
}

string File::readRecord()
{
	if (!openFile(recordPath))
	{
		cout << "打开记录文件异常" << endl;
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
		cout << "关闭记录文件异常" << endl;
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