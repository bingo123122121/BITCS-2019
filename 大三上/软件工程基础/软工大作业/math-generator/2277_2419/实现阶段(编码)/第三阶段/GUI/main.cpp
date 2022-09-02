#include "GUI/mainwindow.h"
#include <cstdlib>
#include <QApplication>

#include "controller/ProblemGenerate.h"


int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    MainWindow w;
    w.show();
    return a.exec();
}
