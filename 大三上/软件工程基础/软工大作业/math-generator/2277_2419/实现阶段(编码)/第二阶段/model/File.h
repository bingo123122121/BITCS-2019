#ifndef File_H
#define File_H

#include <string>
#include <fstream>
#include <iostream>
#include <exception>
#include <algorithm>
#include "Formula.h"
#include "Number.h"
#ifndef ll            //���û�ж���ll
#define ll long long   //����ú���
#endif

using namespace std;

class File
{
public:
    File();
    ~File();
    void removeFile();
    void writeToFile(Formula* formula);
    string readProblem(int num = 20);
    void setProblemPath(string path);


private:
    static const string defaultProblemPath;
    string problemPath;
    fstream fp;

    bool openFile();
    bool closeFile();


    void generateRandom(int* ran, int num);
};

#endif
