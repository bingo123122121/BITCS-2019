//表达式类

#pragma once
#include "Node.h"
#include "Number.h"
#include "Calculater.h"
#include <string>
#include <set>
using namespace std;
#ifndef ll            //如果没有定义ll
#define ll long long   //则定义该宏名
#endif
class Formula
{
public:
	Formula();//构造函数
	~Formula();//构造函数

	void generate();// 构造表达式

	string getStr();//获得中缀表达式
	Number getValue();//获得表达式值
	ll getHash();//获得表达式的hash值


private:
	Calculater cal;//运算器
	string str;//中缀表达式
	Node* root;//表达式二叉树的根
	Number value;//表达式的值
	int opNum;//表达式中符号的数量（不超过10）
	ll hash;//表达式hash值
	int numCnt[21];//表达式中各数字的数量
	int opCnt[4];//表达式中各符号的数量

	static int cnt;	

	
	Node* createChild();//创建子树，用于递归建立二叉树
	void ToString(Node* now);//二叉树转中缀表达式，中序遍历
	Number calValue(Node* now);//计算表达式的值
	void calHash();//计算表达式hash值
	void deleteNode(Node* now);//删除结点
	//void InOrder();
};

