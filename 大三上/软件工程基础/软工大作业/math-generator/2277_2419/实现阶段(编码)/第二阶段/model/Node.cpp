#include "Node.h"

Node::Node()
{
	//初始化
	flag = 0;
	node.num = Number();
	lchild = NULL;
	rchild = NULL;
}

Node::Node(bool flg)
{
	//初始化
	flag = flg;
	if (flag)
		node.op = '\0';
	else
		node.num = Number();
	lchild = NULL;
	rchild = NULL;
}

union nodeData Node::getData()
{
	return node;
}

void Node::setData(Number n)
{
	node.num = n;
}

void Node::setData(char ch)
{
	node.op = ch;
}

bool Node::getType()
{
	return flag;
}