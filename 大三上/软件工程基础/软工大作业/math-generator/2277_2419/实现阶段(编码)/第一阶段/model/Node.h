//�����

#pragma once
#include "Number.h"

//����һ����������
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
	Node();//ȱʡ���캯����Ĭ�ϸýڵ�Ϊ���������Ϊ0
	Node(bool flg);//��֪������͵Ĺ��캯��

	union nodeData getData();//��ȡ�������
	void setData(Number n);//���ý�����
	void setData(char ch);//���ý��ķ���
	bool getType();//��ȡ������ͣ���or���ţ�

	Node* lchild;//���ӽ��ָ��
	Node* rchild;//�Һ��ӽ��ָ��

private:
	bool flag;//��־������ͣ�0��ʾ����1��ʾ����
	union nodeData node;//�������
};

