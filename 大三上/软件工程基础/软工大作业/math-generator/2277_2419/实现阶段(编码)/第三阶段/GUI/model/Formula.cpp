#include "Formula.h"
#include <iostream>
#include <cstdlib>
#include <cstring>
#include <ctime>

char Op[5] = { '+','-','*','/','^' };
int Formula::cnt = 0;


Formula::Formula()
{
	powerFlag = 0;
	generate();
}

Formula::Formula(bool flg)
{
	powerFlag = flg;
	generate();
}

Formula::~Formula()
{
	deleteNode(root);
}

void Formula::generate()
{
	// 用于随机数种子
	cnt += 1;

	//初始化
	cal = Calculater();
	str = "";
	root = NULL;
	value = Number();
	opNum = 0;
	hash = 0;
    memset(numCnt, 0, sizeof(numCnt));
	memset(opCnt, 0, sizeof(opCnt));

	//重置随机种子
	srand((int)time(0) + cnt);

	//建立表达式树
	//根节点，一定产生符号
	int i = rand() % 5;
	root = new Node(1);
	root->setData(Op[i]);
	//更新计数
	opCnt[i]++;
	opNum++;
	//产生左右子树
	if (Op[i] == '^')//规定幂底数只能是数字
	{
		//建立数结点
		Node* l = new Node();
		ll tmp1 = rand() % 21;
		l->setData(Number(tmp1, 1));
		numCnt[tmp1]++;//更新计数
		l->lchild = NULL;
		l->rchild = NULL;

		root->lchild = l;
	}
	else
		root->lchild = createChild();

	if (Op[i] == '/')  //规定除号右边只能是数字，避免右子树表达式的值为0而导致错误
	{
		//建立数结点
		Node* r = new Node();
        ll tmp1 = rand() % 20 + 1;
		r->setData(Number(tmp1, 1));
		numCnt[tmp1]++;//更新计数
		r->lchild = NULL;
		r->rchild = NULL;

		root->rchild = r;
	}
	else if (Op[i] == '^') //规定幂只能是1~3之间的数字
	{
		//建立数结点
		Node* r = new Node();
        ll tmp1 = rand() % 3 + 1;
		r->setData(Number(tmp1, 1));
		numCnt[tmp1]++;//更新计数
		r->lchild = NULL;
		r->rchild = NULL;

		root->rchild = r;
	}
	else
		root->rchild = createChild();
	
	//计算hash值
	calHash();

    //二叉树转中缀表达式
    ToString(root);

    //表达式求值
    value = calValue(root);
}

Node* Formula::createChild()
{
	Node* now;

	//1/2几率产生数，1/2几率产生符号
	int i = rand() % 2;

	if ((!i) && opNum < 10)//当前产生符号
	{
		int j = rand() % 5;//随机产生符号

		//建立符号结点
		now = new Node(1);
		now->setData(Op[j]);
		//更新计数
		opNum++;
		opCnt[i]++;
		//产生左右子树
		if (Op[j] == '^')//规定幂底数只能是数字
		{
			//建立数结点
			Node* l = new Node();
			ll tmp1 = rand() % 21;
			l->setData(Number(tmp1, 1));
			numCnt[tmp1]++;//更新计数
			l->lchild = NULL;
			l->rchild = NULL;

			now->lchild = l;
		}
		else
			now->lchild = createChild();
		if (Op[j] == '/')  //规定除号右边只能是数字，避免右子树表达式的值为0而导致错误
		{
			//建立数结点
			Node* r = new Node();
            ll tmp1 = rand() % 20 + 1;
			r->setData(Number(tmp1, 1));
			numCnt[tmp1]++;//更新计数
			r->lchild = NULL;
			r->rchild = NULL;			

			now->rchild = r;
		}
		else if (Op[j] == '^') //规定幂只能是1~3之间的数字
		{
			//建立数结点
			Node* r = new Node();
            ll tmp1 = rand() % 3 + 1;
			r->setData(Number(tmp1, 1));
			numCnt[tmp1]++;//更新计数
			r->lchild = NULL;
			r->rchild = NULL;

			now->rchild = r;
		}
		else
			now->rchild = createChild();
	}
	else //当前产生数字
	{
		//建立数结点
		now = new Node(0);
		ll tmp1 = rand() % 21;
		now->setData(Number(tmp1, 1));
		numCnt[tmp1]++;//更新计数
		now->lchild = NULL;
		now->rchild = NULL;
	}

	return now;
}


void Formula::ToString(Node* now)
{
	if (!now->getType())//当前结点为数
	{
		ll tmp1 = now->getData().num.getNominator();
		str += to_string(tmp1);
		//由于产生的结点全部为整数（分母均为1），所以只需要提取分子即可。
	}
	else//当前结点为符号
	{		
		if (now->getData().op == '+')//加法，无需加括号直接中序遍历
		{
			ToString(now->lchild);
			str += now->getData().op;
			ToString(now->rchild);
		}
		if (now->getData().op == '-')//减法
		{
			ToString(now->lchild);
			str += now->getData().op;
			if (now->rchild->getType() && (now->rchild->getData().op == '+' || now->rchild->getData().op == '-'))
			{
				str += "(";
				ToString(now->rchild);
				str += ")";
			}
			else
				ToString(now->rchild);
		}
		else if (now->getData().op == '*')
		{
			//遍历左子树			
			if (now->lchild->getType() && (now->lchild->getData().op == '+' || now->lchild->getData().op == '-'))
			{
				//判断是否加括号：乘法子树为加减法时需要加括号
				str += "(";
				ToString(now->lchild);
				str += ")";
			}
			else
				ToString(now->lchild);

			str += now->getData().op;

			//遍历右子树
			if (now->rchild->getType() && (now->rchild->getData().op == '+' || now->rchild->getData().op == '-'))
			{
				str += "(";
				ToString(now->rchild);
				str += ")";
			}
			else
				ToString(now->rchild);
		}
		else if (now->getData().op == '/')
		{
			//遍历左子树
			if (now->lchild->getType() && (now->lchild->getData().op == '+' || now->lchild->getData().op == '-'))
			{
				str += "(";
				ToString(now->lchild);
				str += ")";
			}
			else
				ToString(now->lchild);

			str += now->getData().op;

			//遍历右子树
			ToString(now->rchild); //除法右子树一定为数，所以不需要加括号
		}
		else if (now->getData().op == '^')
		{
			//遍历左子树
			ToString(now->lchild);

			if (powerFlag)
				str += "**";
			else
				str += "^";

			//遍历右子树
			ToString(now->rchild); //右子树一定为数，所以不需要加括号
		}
	}	
}

Number Formula::calValue(Node* now)
{
	if (!now->getType())//当前结点为数字
		return now->getData().num;
	else//当前结点为符号
	{
		Number num1 = calValue(now->lchild);
		Number num2 = calValue(now->rchild);

		if (now->getData().op == '+')//加
			return cal.add(num1, num2);
		else if (now->getData().op == '-')//减
			return cal.minus(num1, num2);
		else if (now->getData().op == '*')//乘
			return cal.mutiplate(num1, num2);
		if (now->getData().op == '/')//除
			return cal.divide(num1, num2);
        else//乘方
			return cal.power(num1, num2);
	}
}

string Formula::getStr()
{
	return str;
}

Number Formula::getValue()
{
	return value;
}


void Formula::calHash()
{
	//添加数的hash值
	for (int i = 0; i < 21; i++)
	{
		if (numCnt[i] != 0)
		{
            hash += 97 * i + 71;//数
            hash += 97 * numCnt[i] + 71;//数出现的次数
		}
	}
	//添加符号的hash值
	for (int i = 0; i < 5; i++)
	{
		if (opCnt[i] != 0)
		{
            hash += 97 * int(Op[i]) + 71;//符号
            hash += 97 * opCnt[i] + 71;//符号出现的次数
		}
	}
}

ll Formula::getHash()
{
	return hash;
}

void Formula::deleteNode(Node* now)
{
	if (now->getType())//当前结点是符号
	{
		deleteNode(now->lchild);
		deleteNode(now->rchild);
	}
	delete now;
}
