#include <iostream>
#include "view/Cmd.h"
#include "controller/ProblemGenerate.h"
#include "controller/ProblemExtraction.h"
#include "controller/AnswerJudge.h"

int main()
{
    Cmd cmd = Cmd();
    char str[100];
    while (cin.getline(str, 100))
    {
        //cout << str << endl;
        int stat = cmd.parse(str);
        if (stat == -1)
        {
            return 0;
        }
    }
}
