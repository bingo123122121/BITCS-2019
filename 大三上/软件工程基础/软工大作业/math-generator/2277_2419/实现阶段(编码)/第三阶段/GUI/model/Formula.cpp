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
	// �������������
	cnt += 1;

	//��ʼ��
	cal = Calculater();
	str = "";
	root = NULL;
	value = Number();
	opNum = 0;
	hash = 0;
    memset(numCnt, 0, sizeof(numCnt));
	memset(opCnt, 0, sizeof(opCnt));

	//�����������
	srand((int)time(0) + cnt);

	//�������ʽ��
	//���ڵ㣬һ����������
	int i = rand() % 5;
	root = new Node(1);
	root->setData(Op[i]);
	//���¼���
	opCnt[i]++;
	opNum++;
	//������������
	if (Op[i] == '^')//�涨�ݵ���ֻ��������
	{
		//���������
		Node* l = new Node();
		ll tmp1 = rand() % 21;
		l->setData(Number(tmp1, 1));
		numCnt[tmp1]++;//���¼���
		l->lchild = NULL;
		l->rchild = NULL;

		root->lchild = l;
	}
	else
		root->lchild = createChild();

	if (Op[i] == '/')  //�涨�����ұ�ֻ�������֣��������������ʽ��ֵΪ0�����´���
	{
		//���������
		Node* r = new Node();
        ll tmp1 = rand() % 20 + 1;
		r->setData(Number(tmp1, 1));
		numCnt[tmp1]++;//���¼���
		r->lchild = NULL;
		r->rchild = NULL;

		root->rchild = r;
	}
	else if (Op[i] == '^') //�涨��ֻ����1~3֮�������
	{
		//���������
		Node* r = new Node();
        ll tmp1 = rand() % 3 + 1;
		r->setData(Number(tmp1, 1));
		numCnt[tmp1]++;//���¼���
		r->lchild = NULL;
		r->rchild = NULL;

		root->rchild = r;
	}
	else
		root->rchild = createChild();
	
	//����hashֵ
	calHash();

    //������ת��׺���ʽ
    ToString(root);

    //���ʽ��ֵ
    value = calValue(root);
}

Node* Formula::createChild()
{
	Node* now;

	//1/2���ʲ�������1/2���ʲ�������
	int i = rand() % 2;

	if ((!i) && opNum < 10)//��ǰ��������
	{
		int j = rand() % 5;//�����������

		//�������Ž��
		now = new Node(1);
		now->setData(Op[j]);
		//���¼���
		opNum++;
		opCnt[i]++;
		//������������
		if (Op[j] == '^')//�涨�ݵ���ֻ��������
		{
			//���������
			Node* l = new Node();
			ll tmp1 = rand() % 21;
			l->setData(Number(tmp1, 1));
			numCnt[tmp1]++;//���¼���
			l->lchild = NULL;
			l->rchild = NULL;

			now->lchild = l;
		}
		else
			now->lchild = createChild();
		if (Op[j] == '/')  //�涨�����ұ�ֻ�������֣��������������ʽ��ֵΪ0�����´���
		{
			//���������
			Node* r = new Node();
            ll tmp1 = rand() % 20 + 1;
			r->setData(Number(tmp1, 1));
			numCnt[tmp1]++;//���¼���
			r->lchild = NULL;
			r->rchild = NULL;			

			now->rchild = r;
		}
		else if (Op[j] == '^') //�涨��ֻ����1~3֮�������
		{
			//���������
			Node* r = new Node();
            ll tmp1 = rand() % 3 + 1;
			r->setData(Number(tmp1, 1));
			numCnt[tmp1]++;//���¼���
			r->lchild = NULL;
			r->rchild = NULL;

			now->rchild = r;
		}
		else
			now->rchild = createChild();
	}
	else //��ǰ��������
	{
		//���������
		now = new Node(0);
		ll tmp1 = rand() % 21;
		now->setData(Number(tmp1, 1));
		numCnt[tmp1]++;//���¼���
		now->lchild = NULL;
		now->rchild = NULL;
	}

	return now;
}


void Formula::ToString(Node* now)
{
	if (!now->getType())//��ǰ���Ϊ��
	{
		ll tmp1 = now->getData().num.getNominator();
		str += to_string(tmp1);
		//���ڲ����Ľ��ȫ��Ϊ��������ĸ��Ϊ1��������ֻ��Ҫ��ȡ���Ӽ��ɡ�
	}
	else//��ǰ���Ϊ����
	{		
		if (now->getData().op == '+')//�ӷ������������ֱ���������
		{
			ToString(now->lchild);
			str += now->getData().op;
			ToString(now->rchild);
		}
		if (now->getData().op == '-')//����
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
			//����������			
			if (now->lchild->getType() && (now->lchild->getData().op == '+' || now->lchild->getData().op == '-'))
			{
				//�ж��Ƿ�����ţ��˷�����Ϊ�Ӽ���ʱ��Ҫ������
				str += "(";
				ToString(now->lchild);
				str += ")";
			}
			else
				ToString(now->lchild);

			str += now->getData().op;

			//����������
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
			//����������
			if (now->lchild->getType() && (now->lchild->getData().op == '+' || now->lchild->getData().op == '-'))
			{
				str += "(";
				ToString(now->lchild);
				str += ")";
			}
			else
				ToString(now->lchild);

			str += now->getData().op;

			//����������
			ToString(now->rchild); //����������һ��Ϊ�������Բ���Ҫ������
		}
		else if (now->getData().op == '^')
		{
			//����������
			ToString(now->lchild);

			if (powerFlag)
				str += "**";
			else
				str += "^";

			//����������
			ToString(now->rchild); //������һ��Ϊ�������Բ���Ҫ������
		}
	}	
}

Number Formula::calValue(Node* now)
{
	if (!now->getType())//��ǰ���Ϊ����
		return now->getData().num;
	else//��ǰ���Ϊ����
	{
		Number num1 = calValue(now->lchild);
		Number num2 = calValue(now->rchild);

		if (now->getData().op == '+')//��
			return cal.add(num1, num2);
		else if (now->getData().op == '-')//��
			return cal.minus(num1, num2);
		else if (now->getData().op == '*')//��
			return cal.mutiplate(num1, num2);
		if (now->getData().op == '/')//��
			return cal.divide(num1, num2);
        else//�˷�
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
	//�������hashֵ
	for (int i = 0; i < 21; i++)
	{
		if (numCnt[i] != 0)
		{
            hash += 97 * i + 71;//��
            hash += 97 * numCnt[i] + 71;//�����ֵĴ���
		}
	}
	//��ӷ��ŵ�hashֵ
	for (int i = 0; i < 5; i++)
	{
		if (opCnt[i] != 0)
		{
            hash += 97 * int(Op[i]) + 71;//����
            hash += 97 * opCnt[i] + 71;//���ų��ֵĴ���
		}
	}
}

ll Formula::getHash()
{
	return hash;
}

void Formula::deleteNode(Node* now)
{
	if (now->getType())//��ǰ����Ƿ���
	{
		deleteNode(now->lchild);
		deleteNode(now->rchild);
	}
	delete now;
}
