#pragma once
#include "Number.h"
#ifndef ll            //如果没有定义ll
#define ll long long   //则定义该宏名
#endif
class Calculater
{
public:
	Calculater();
	//Calculater(ll numerator, ll denominator);
	Number reduction(Number n);//约分
	Number add(Number num1,Number num2);//加法
	Number minus(Number num1, Number num2);//减法
	Number mutiplate(Number num1, Number num2);//乘法
	Number divide(Number num1, Number num2);//除法
	Number power(Number num1, Number num2);//乘方
private:
	ll gcd(ll n1, ll n2);//辗转相除法求最大公约数

};

