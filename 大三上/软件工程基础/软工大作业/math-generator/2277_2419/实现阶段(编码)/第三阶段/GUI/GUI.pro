QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

CONFIG += c++11

# You can make your code fail to compile if it uses deprecated APIs.
# In order to do so, uncomment the following line.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

SOURCES += \
    controller/AnswerJudge.cpp \
    controller/ManageRecord.cpp \
    controller/ProblemExtraction.cpp \
    controller/ProblemGenerate.cpp \
    main.cpp \
    GUI/mainwindow.cpp \
    model/Calculater.cpp \
    model/File.cpp \
    model/Formula.cpp \
    model/Node.cpp \
    model/Number.cpp

HEADERS += \
    GUI/mainwindow.h \
    controller/AnswerJudge.h \
    controller/ManageRecord.h \
    controller/ProblemExtraction.h \
    controller/ProblemGenerate.h \
    model/Calculater.h \
    model/File.h \
    model/Formula.h \
    model/Node.h \
    model/Number.h

FORMS += \
    GUI\mainwindow.ui

# Default rules for deployment.
qnx: target.path = /tmp/$${TARGET}/bin
else: unix:!android: target.path = /opt/$${TARGET}/bin
!isEmpty(target.path): INSTALLS += target

DISTFILES += \
    problem.txt

RESOURCES += \
    source.qrc
