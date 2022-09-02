#include <iostream>
#include "view/Cmd.h"
#include "controller/ProblemGenerate.h"
#include "controller/ProblemExtraction.h"
#include "controller/AnswerJudge.h"

int main()
{
    cout << "请输入乘方的表示形式：0代表^，1代表**" << endl;
    bool flg = 0;
    cin >> flg;
    char tmp;
    tmp = getchar();//吸收回车
    Cmd cmd = Cmd(flg);
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

    //ProblemGenerate p = ProblemGenerate();
    //p.generate();

    //ProblemExtraction p = ProblemExtraction();
    //p.extract(20);
    //AnswerJudge a = AnswerJudge();
    //a.readAnswer(&p);
    //for (int i = -1; i < 11; i++)
    //{
    //    cout << a.getAnswer(i) << endl;
    //}
    
    //cout << "1" << endl;
    //cout << p.getProblem(0) << endl;
    //cout << "2" << endl;
    //cout << p.getAnswer() << endl;
    //cout << "3" << endl;

    //AnswerJudge a = AnswerJudge();
    //a.readAnswer(&p);
    //cout << "4" << endl;

    //Number* number = new Number[10];
    //bool stat[10] = {false};
    //for (int i = 0; i < 10; i++)
    //{
    //    ll a, b;
    //    cin >> a >> b;
    //    number[i].setNumber(a, b);
    //}

    //cout << a.judgeAnswer(number, stat) << endl;

    //fstream fp;
    //char s[200];
    //fp.open("problem.txt");
    //while (!fp.eof())
    //{
    //    fp.getline(s, 200);
    //    cout << s << endl;
    //}

}
