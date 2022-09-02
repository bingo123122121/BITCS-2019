#include "ManageRecord.h"

const int ManageRecord::maxRecordNum = 20;

ManageRecord::ManageRecord()
{
	file = new File();
	num = 0;
}

ManageRecord::~ManageRecord()
{
	delete file;
}

void ManageRecord::writeRecord(int correct, int sum)
{
	if (correct > sum || correct < 0 || sum > 1000)
	{
		cout << "参数错误" << endl;
		return;
	}

	time_t curTime;
	time(&curTime);

	string record = "";

	char temp[50] = { '\0' };
	ctime_s(temp, 50, &curTime);

	record = temp;
	record += "correct:" + to_string(correct) + ", wrong:" + to_string(sum - correct) + ", sum:" + to_string(sum) + ".";
	file->writeToFile(record);
	return;
}

void ManageRecord::readRecord()
{
	string records = file->readRecord();
    int pre = 0, now = 0;
	bool round = false;
	while ((now = records.find('#', now) + 1) != 0)
	{
		record[num % maxRecordNum] = records.substr(pre, now - pre - 1);

		pre = now;
		if (num == maxRecordNum - 1)
		{
			round = true;
		}
		num = (num + 1) % maxRecordNum;
	}
	if (round)
	{
		string temp[maxRecordNum];
		for (int i = 0; i < maxRecordNum; i++)
		{
			temp[i] = record[(num + i) % maxRecordNum];
		}
		for (int i = 0; i < maxRecordNum; i++)
		{
			record[i] = temp[i];
		}
		num = maxRecordNum;
	}
}

string ManageRecord::getRecord(int i)
{
	if (i < 0 || i > num - 1)
	{
		cout << "请输入0~" << num - 1 << "的整数！" << endl;
		return "";
	}
	return record[i];
}

int ManageRecord::getNum()
{
	return num;
}

void ManageRecord::clearRecord()
{
	file->removeFile(file->getRecordPath());
}
