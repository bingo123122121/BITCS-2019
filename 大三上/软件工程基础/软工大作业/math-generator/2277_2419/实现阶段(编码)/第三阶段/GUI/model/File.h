#ifndef File_H
#define File_H

#include <string>
#include <sstream>
#include <fstream>
#include <iostream>
#include <exception>
#include <algorithm>
#include <ctime>
#include "Formula.h"
#include "Number.h"
#ifndef ll            //如果没有定义ll
#define ll long long   //则定义该宏名
#endif

using namespace std;

class File
{
public:
    File();
    ~File();
    void removeFile(string path);
    void writeToFile(Formula* formula);
    void writeToFile(string record);
    string readProblem(int num = 20);
    string readRecord();
    void setProblemPath(string path);
    void setRecordPath(string path);
    string getProblemPath();
    string getRecordPath();


private:
    static const string defaultProblemPath;
    static const string defaultRecordPath;
    string problemPath;
    string recordPath;
    fstream fp;

    bool openFile(string path);
    bool closeFile();


    void generateRandom(int* ran, int num);
};

#endif
