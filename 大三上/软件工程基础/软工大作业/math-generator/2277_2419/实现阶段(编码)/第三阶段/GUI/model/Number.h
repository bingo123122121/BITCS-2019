//������

#pragma once
#include <cstdio>
#ifndef ll            //���û�ж���ll
#define ll long long   //����ú���
#endif
class Number
{
public:
	Number();//ȱʡ���캯����Ĭ�Ϸ�����ֵΪ0������0��ĸ1��
	Number(ll nominator, ll denominator);//���캯��
	ll getNominator();//��÷���
	ll getDenominator();//��÷�ĸ
	void setNumber(ll nominator = 0, ll denominator = 1);//���÷��ӡ���ĸ
	bool operator==(const Number& num);

private:
	ll nominator;//����
	ll denominator;//��ĸ
};

