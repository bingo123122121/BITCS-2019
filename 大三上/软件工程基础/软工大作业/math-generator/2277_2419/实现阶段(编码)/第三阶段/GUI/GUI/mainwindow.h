#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QWidget>
#include <QSplitter>
#include <QTableWidget>
#include <QListWidget>
#include <QString>
#include <QImage>
#include <QLabel>
#include <QLineEdit>
#include <QDebug>
#include <QToolButton>
#include <QAction>
#include <QMenu>
#include <QVBoxLayout>
#include <QMouseEvent>
#include <QButtonGroup>
#include <QTextEdit>
#include <QTimerEvent>
#include <QTimer>

#include "../controller/ProblemGenerate.h"
#include "../controller/ProblemExtraction.h"
#include "../controller/AnswerJudge.h"
#include "../controller/ManageRecord.h"

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QWidget
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

    int timerId;

private slots:
    void on_closeButton_clicked();
    void on_minimizeButton_clicked();
    void ShowStartInfo();
    void ShowFirstQuestion();
    void ShowAnswer();
    void ShowNextQuestion();
    void ShowResult();
    void ShowHistory();
    void ClearHistory();
    void Timer();

    void on_checkButton_clicked();

private:
    Ui::MainWindow *ui;
    int num; //题目数量
    int answered;//已答题目数量
    int correct;//当前正确数量
    QPoint m_point;
    int count;

    ProblemGenerate* pg;//题目生成
    ProblemExtraction* pe;//题目抽取
    AnswerJudge* aj;//答案判断
    ManageRecord* mr;//答题记录管理

    QButtonGroup *checkGroup;
    QLabel* welcomeLabel;//初始界面欢迎
    QLabel* inputMsg;//提示用户输入
    QLabel* questionLabel;//题目字符串
    QLabel* alertLabel;//显示正确答案、提示输入错误等消息
    QLineEdit*  numInputEdit;//输入框
    QToolButton* startButton;//开始答题按钮
    QToolButton* submitButton;//提交当前答案按钮
    QToolButton* nextQuestionButton;//下一题按钮
    QToolButton* endButton;//结束按钮
    QToolButton* returnButton;//返回主界面按钮
    QToolButton* historyButton;//历史记录按钮
    QToolButton* clearButton;//清除历史记录按钮
    QTextEdit* historyContent;//历史记录内容

    bool judgeInput();

protected:
    void mousePressEvent(QMouseEvent *event); //鼠标按下事件
    void mouseMoveEvent(QMouseEvent *event);  //鼠标移动事件
    void timerEvent(QTimerEvent* event);
};
#endif // MAINWINDOW_H
