#pragma once
#include "Number.h"
#ifndef ll            //���û�ж���ll
#define ll long long   //����ú���
#endif
class Calculater
{
public:
	Calculater();
	//Calculater(ll numerator, ll denominator);
	Number reduction(Number n);//Լ��
	Number add(Number num1,Number num2);//�ӷ�
	Number minus(Number num1, Number num2);//����
	Number mutiplate(Number num1, Number num2);//�˷�
	Number divide(Number num1, Number num2);//����
	Number power(Number num1, Number num2);//�˷�
private:
	ll gcd(ll n1, ll n2);//շת����������Լ��

};

