#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QGraphicsDropShadowEffect>


MainWindow::MainWindow(QWidget *parent)
    : QWidget(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    // 第一个参数是设置无边框。第二个参数是允许任务栏按钮右键菜单，第三个参数是允许最小化与还原
    this->setAttribute(Qt::WA_TranslucentBackground);
    this->setWindowFlags(this->windowFlags()|Qt::FramelessWindowHint | Qt::WindowSystemMenuHint | Qt::WindowMinimizeButtonHint);

    //设置阴影
    QGraphicsDropShadowEffect *shadow = new QGraphicsDropShadowEffect(this);
    shadow->setOffset(0, 0);//设置向哪个方向产生阴影效果，（0,0）表示向四周发散
    shadow->setColor(QColor(38, 78, 119,255));//阴影颜色
    shadow->setBlurRadius(20); //模糊度
    ui->widget->setGraphicsEffect(shadow);

    m_point = QPoint();

    //新建选择分组
    checkGroup = new QButtonGroup(this);
    checkGroup->addButton(ui->check1,0);
    checkGroup->addButton(ui->check2,1);
    ui->check1->setChecked(1);

    welcomeLabel = new QLabel(ui->widget);
    welcomeLabel->setText("欢迎来到四则运算器");
    welcomeLabel->setGeometry(200,100,400,100);
    welcomeLabel->setFont(QFont( "宋体" , 20 ,  QFont::Normal));
    welcomeLabel->setAlignment(Qt::AlignCenter);
    welcomeLabel->hide();

    inputMsg = new QLabel(ui->widget);
    inputMsg->setText("");
    inputMsg->setGeometry(250,300,240,30);
    inputMsg->setFont(QFont( "宋体" , 12 ,  QFont::Normal));
    inputMsg->setAlignment(Qt::AlignVCenter|Qt::AlignRight);
    inputMsg->hide();

    questionLabel = new QLabel(ui->widget);
    questionLabel->setText("");
    questionLabel->setGeometry(100,100,600,100);
    questionLabel->setFont(QFont( "宋体" , 12 ,  QFont::Normal));
    questionLabel->setAlignment(Qt::AlignCenter);
    questionLabel->hide();

    alertLabel = new QLabel(ui->widget);
    alertLabel->setText("");
    alertLabel->setGeometry(100,340,440,30);
    alertLabel->setFont(QFont( "宋体" , 10 ,  QFont::Normal));
    alertLabel->setStyleSheet("QLabel{color:rgb(255, 10, 10)}");
    alertLabel->setAlignment(Qt::AlignHCenter | Qt::AlignRight);
    alertLabel->hide();

    numInputEdit = new QLineEdit(ui->widget);
    numInputEdit->setGeometry(490,300,50,30);
    numInputEdit->setStyleSheet("QLineEdit{border:none;border-bottom:1px solid rgb(150, 150, 150);}");
    numInputEdit->setFont(QFont( "宋体" , 12 ,  QFont::Normal));
    numInputEdit->setAlignment(Qt::AlignCenter);
    numInputEdit->hide();


    startButton = new QToolButton(ui->widget);
    startButton->setText("开始答题");
    startButton->setGeometry(340,400,120,40);
    startButton->setStyleSheet("QToolButton{border:2px solid;\
                                                    border-color:rgb(200,200,200);border-radius:15px;}\
                                                QToolButton::hover{background-color:rgb(230,230,230)}");
    startButton->setFont(QFont( "宋体" , 12 ,  QFont::Normal));
    startButton->hide();

    submitButton = new QToolButton(ui->widget);
    submitButton->setText("提交");
    submitButton->setGeometry(340,400,120,40);
    submitButton->setStyleSheet("QToolButton{border:2px solid;\
                                                  border-color:rgb(200,200,200);border-radius:15px;}\
                                              QToolButton::hover{background-color:rgb(230,230,230)}");
    submitButton->setFont(QFont( "宋体" , 10 ,  QFont::Normal));
    submitButton->hide();

    nextQuestionButton = new QToolButton(ui->widget);
    nextQuestionButton->setText("下一题");
    nextQuestionButton->setGeometry(340,400,120,40);
    nextQuestionButton->setStyleSheet("QToolButton{border:2px solid;\
                                                   border-color:rgb(200,200,200);border-radius:15px;}\
                                               QToolButton::hover{background-color:rgb(230,230,230)}");
    nextQuestionButton->setFont(QFont( "宋体" , 10 ,  QFont::Normal));
    nextQuestionButton->hide();

    endButton = new QToolButton(ui->widget);
    endButton->setText("结束");
    endButton->setGeometry(480,400,120,40);
    endButton->setStyleSheet("QToolButton{border:2px solid;\
                                                 border-color:rgb(200,200,200);border-radius:15px;}\
                                             QToolButton::hover{background-color:rgb(230,230,230)}");
    endButton->setFont(QFont( "宋体" , 10 ,  QFont::Normal));
    endButton->hide();

    returnButton = new QToolButton(ui->widget);
    returnButton->setText("返回");
    returnButton->setGeometry(340,400,120,40);
    returnButton->setStyleSheet("QToolButton{border:2px solid;\
                                                  border-color:rgb(200,200,200);border-radius:15px;}\
                                              QToolButton::hover{background-color:rgb(230,230,230)}");
    returnButton->setFont(QFont( "宋体" , 10 ,  QFont::Normal));
    returnButton->hide();

    historyButton = new QToolButton(ui->widget);
    historyButton->setText("查看历史记录");
    historyButton->setGeometry(20,520,140,40);
    historyButton->setStyleSheet("QToolButton{border:2px solid;\
                                                 border-color:rgb(200,200,200);border-radius:15px;}\
                                             QToolButton::hover{background-color:rgb(230,230,230)}");
    historyButton->setFont(QFont( "宋体" , 10 ,  QFont::Normal));
    historyButton->hide();

    clearButton = new QToolButton(ui->widget);
    clearButton->setText("清空");
    clearButton->setGeometry(100,460,60,40);
    clearButton->setStyleSheet("QToolButton{border:2px solid;\
                                                border-color:rgb(200,200,200);border-radius:15px;}\
                                            QToolButton::hover{background-color:rgb(230,230,230)}");
    clearButton->setFont(QFont( "宋体" , 10 ,  QFont::Normal));
    clearButton->hide();

    historyContent = new QTextEdit(ui->widget);
    historyContent->setGeometry(140,100,560,350);
    historyContent->setFont(QFont( "宋体" , 10 ,  QFont::Normal));
    historyContent->setReadOnly(true);
    historyContent->hide();

    ui->clockLabel->hide();

    connect(startButton,SIGNAL(clicked()),this,SLOT(ShowFirstQuestion()));//点击开始答题后进入答题页面
    connect(startButton,SIGNAL(clicked()),this,SLOT(Timer()));
    connect(submitButton,SIGNAL(clicked()),this,SLOT(ShowAnswer()));
    //connect(this,SIGNAL((count==0)),this,SLOT(ShowAnswer()));
    connect(nextQuestionButton,SIGNAL(clicked()),this,SLOT(ShowNextQuestion()));
    connect(nextQuestionButton,SIGNAL(clicked()),this,SLOT(Timer()));
    connect(endButton,SIGNAL(clicked()),this,SLOT(ShowResult()));
    connect(returnButton,SIGNAL(clicked()),this,SLOT(ShowStartInfo()));
    connect(historyButton,SIGNAL(clicked()),this,SLOT(ShowHistory()));
    connect(clearButton,SIGNAL(clicked()),this,SLOT(ClearHistory()));

}

MainWindow::~MainWindow()
{
    delete pg;
    delete pe;
    delete aj;
    delete mr;
    delete ui;

    delete checkGroup;
    delete welcomeLabel;//初始界面欢迎
    delete inputMsg;//提示用户输入
    delete questionLabel;//题目字符串
    delete alertLabel;//显示正确答案、提示输入错误等消息
    delete numInputEdit;//输入框
    delete startButton;//开始答题按钮
    delete submitButton;//提交当前答案按钮
    delete nextQuestionButton;//下一题按钮
    delete endButton;//结束按钮
    delete returnButton;//返回主界面按钮
    delete historyButton;//历史记录按钮
    delete clearButton;//清除历史记录按钮
    delete historyContent;//历史记录内容
}

bool MainWindow::judgeInput()
{
    bool ok;//判断输入的是否为数字
    int input = numInputEdit->text().toInt(&ok);
    //判断输入题目数量的合法性
    if(ok && input>=1 && input<=1000)
        return 1;
    else
        return 0;
}

void MainWindow::on_closeButton_clicked()
{
    this->close();
}

void MainWindow::on_minimizeButton_clicked()
{
    this->showMinimized();
}

void MainWindow::ShowStartInfo()
{
    //初始化
    num = 0;
    answered = 0;
    correct = 0;

    //显示的字符更新
    inputMsg->setText("请输入本次做题数量：");
    //控件显示更新
    ui->label->setText("Hello!");
    questionLabel->hide();//关闭答题结束后界面的控件
    returnButton->hide();//关闭答题结束后界面的控件
    inputMsg->show();
    numInputEdit->show();
    startButton->show();
    historyButton->show();
    welcomeLabel->show();
}

void MainWindow::ShowFirstQuestion()
{
    bool flag = judgeInput();
    if(!flag)
    {
        alertLabel->setText("请输入1~1000的整数！");
        alertLabel->show();
        return;
    }

    num = numInputEdit->text().toInt();
    numInputEdit->clear();
    //显示的字符更新
    //抽取题目，读取答案
    //题目存在pe->problems[]中，答案存在aj->standardAnswer[]中
    pe->extract(num);
    aj->readAnswer(pe);

    //cout << "pro:" << pe->getProblem(answered) << endl;
    cout << "ans:" << pe->getAnswer() << endl;

    //这里需要调用函数传入题目
    QString questionStr = QString::fromStdString(pe->getProblem(answered));

    questionLabel->setText(questionStr);
    inputMsg->setText("请输入结果：");

    //控件显示更新
    ui->label->setText(QString::number(answered+1)+"/"+QString::number(num));
    questionLabel->show();
    welcomeLabel->hide();
    startButton->hide();
    historyButton->hide();
    submitButton->show();
    endButton->hide();
    alertLabel->hide();
}

void MainWindow::ShowAnswer()
{
    if(numInputEdit->text() == "")
    {
        alertLabel->setText("请输入你的答案！");
        alertLabel->show();
    }
    else
    {
        killTimer(timerId); //停止定时器
        cout<<"----------start judge----------"<<endl;
        //显示的字符更新
        //比较两个答案是否相同
        QString str = numInputEdit->text();
        //用户答案处理成Number
        ll fz = 0x3f3f3f3f, fm = 1;
        sscanf(str.toStdString().c_str(), "%lld/%lld", &fz, &fm);
        Number answer = Number(fz, fm);

        cout<<"you:"<<answer.getNominator()<<" "<<answer.getDenominator()<<endl;
        if(aj->judgeAnswer(answer, answered))
        {
            correct++;
            aj->getAnswer(answered);
            str="回答正确！";
        }
        else
        {
            str="回答错误，正确答案为：" + QString::fromStdString(aj->getAnswer(answered));
        }
        alertLabel->setText(str);

        answered++;

        //控件显示更新
        ui->clockLabel->hide();
        submitButton->hide();
        alertLabel->show();
        endButton->show();
        if(answered < num)//答题完毕，显示结果
        {
             nextQuestionButton->show();
        }
    }
}

void MainWindow::ShowNextQuestion()
{
    numInputEdit->clear();
    //显示的字符更新
    //这里需要调用函数传入题目
    QString questionStr = QString::fromStdString(pe->getProblem(answered));
    questionLabel->setText(questionStr);
    //控件显示更新
    ui->label->setText(QString::number(answered+1)+"/"+QString::number(num));
    nextQuestionButton->hide();
    submitButton->show();
    endButton->hide();
    alertLabel->hide();
}

void MainWindow::ShowResult()
{
    numInputEdit->clear();
    //显示的字符更新
   QString str = "您本次共答题"+QString::number(answered, 10)+"道，正确"+\
           QString::number(correct, 10)+"道，错误"+QString::number(answered - correct, 10)+"道。";
   questionLabel->setText(str);
   mr->writeRecord(correct,answered);
   //控件显示更新
   ui->label->setText(QString::number(answered)+"/"+QString::number(num));
   inputMsg->hide();
   numInputEdit->hide();
   nextQuestionButton->hide();
   endButton->hide();
   returnButton->show();
   historyButton->show();
   alertLabel->hide();
}

void MainWindow::ShowHistory()
{
    if (historyContent->isHidden())
    {
        mr->readRecord();
        int t = mr->getNum();
        QString s = "";
        for (int i = 0; i < t; i++)
        {
            s+= QString::fromStdString(mr->getRecord(i)) + "\n";
        }
        historyContent->setText(s);

        //控件显示更新
        historyContent->show();
        clearButton->show();
        historyButton->setText("关闭历史记录");
    }
    else
    {
        //控件显示更新
        historyContent->hide();
        clearButton->hide();
        historyButton->setText("查看历史记录");
    }
}

void MainWindow::ClearHistory()
{
    mr->clearRecord();
    historyContent->clear();
}

void MainWindow::Timer()
{
    if (answered == num)
        return;
    count=20;
    ui->clockLabel->setText("倒计时："+QString::number(count));
    ui->clockLabel->show();
    timerId=startTimer(1000);
}

void MainWindow::timerEvent(QTimerEvent* event)
{
    if(event->timerId()==timerId)
    {
        if(count!=1)
        {
            count--;
            ui->clockLabel->setText("倒计时："+QString::number(count));
        }
        else
        {
            numInputEdit->setText(" ");
            ShowAnswer();
        }
    }
}


//鼠标按下
void MainWindow::mousePressEvent(QMouseEvent *event)
{
   if(event->button() == Qt::LeftButton)
   {
       m_point = event->globalPos() - pos(); //计算移动量
       event->accept();
   }
}

//鼠标移动
void MainWindow::mouseMoveEvent(QMouseEvent *event)
{
    if(event->buttons() & Qt::LeftButton)
    {
        move(event->globalPos() - m_point);
        event->accept();
    }
}

void MainWindow::on_checkButton_clicked()
{
    if(ui->check2->isChecked())
    {
        //初始化题目
        pg = new ProblemGenerate(1);
    }
    else
    {
        pg = new ProblemGenerate(0);
    }

    pe = new ProblemExtraction();
    aj = new AnswerJudge();
    mr = new ManageRecord();

    //题目初始化完成后显示开始答题样式
    ShowStartInfo();

    //更新控件
    ui->check1->hide();
    ui->check2->hide();
    ui->checkLabel->hide();
    ui->checkButton->hide();

}

