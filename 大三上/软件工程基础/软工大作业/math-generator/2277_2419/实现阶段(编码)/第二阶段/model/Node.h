//结点类

#pragma once
#include "Number.h"

//定义一个联合类型
union nodeData {
	nodeData() {
		num = Number();
		op = '\0'; 
	};
	Number num;
	char op;
};

class Node
{
public:
	Node();//缺省构造函数，默认该节点为数结点且数为0
	Node(bool flg);//已知结点类型的构造函数

	union nodeData getData();//获取结点内容
	void setData(Number n);//设置结点的数
	void setData(char ch);//设置结点的符号
	bool getType();//获取结点类型（数or符号）

	Node* lchild;//左孩子结点指针
	Node* rchild;//右孩子结点指针

private:
	bool flag;//标志结点类型，0表示数，1表示符号
	union nodeData node;//结点内容
};

