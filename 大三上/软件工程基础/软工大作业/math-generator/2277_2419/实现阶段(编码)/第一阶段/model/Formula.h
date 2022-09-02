//���ʽ��

#pragma once
#include "Node.h"
#include "Number.h"
#include "Calculater.h"
#include <string>
#include <set>
using namespace std;
#ifndef ll            //���û�ж���ll
#define ll long long   //����ú���
#endif
class Formula
{
public:
	Formula();//���캯��
	~Formula();//���캯��

	void generate();// ������ʽ

	string getStr();//�����׺���ʽ
	Number getValue();//��ñ��ʽֵ
	ll getHash();//��ñ��ʽ��hashֵ


private:
	Calculater cal;//������
	string str;//��׺���ʽ
	Node* root;//���ʽ�������ĸ�
	Number value;//���ʽ��ֵ
	int opNum;//���ʽ�з��ŵ�������������10��
	ll hash;//���ʽhashֵ
	int numCnt[21];//���ʽ�и����ֵ�����
	int opCnt[4];//���ʽ�и����ŵ�����

	static int cnt;	

	
	Node* createChild();//�������������ڵݹ齨��������
	void ToString(Node* now);//������ת��׺���ʽ���������
	Number calValue(Node* now);//������ʽ��ֵ
	void calHash();//������ʽhashֵ
	void deleteNode(Node* now);//ɾ�����
	//void InOrder();
};

