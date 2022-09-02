// 结果记录模块
#ifndef ManageRecord_H
#define ManageRecord_H

#include <ctime>
#include "../model/File.h"

class ManageRecord
{
public:
	ManageRecord();
	~ManageRecord();
	void writeRecord(int correct, int sum);
	void readRecord();
	string getRecord(int i);
	int getNum();
	void clearRecord();


private:
	static const int maxRecordNum;
	File* file;
	string record[100];
	int num;
};


#endif