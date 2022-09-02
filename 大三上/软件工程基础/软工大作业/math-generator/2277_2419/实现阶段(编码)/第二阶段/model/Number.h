//分数类

#pragma once
#include <cstdio>
#ifndef ll            //如果没有定义ll
#define ll long long   //则定义该宏名
#endif
class Number
{
public:
	Number();//缺省构造函数，默认分数的值为0（分子0分母1）
	Number(ll nominator, ll denominator);//构造函数
	ll getNominator();//获得分子
	ll getDenominator();//获得分母
	void setNumber(ll nominator = 0, ll denominator = 1);//设置分子、分母
	bool operator==(const Number& num);

private:
	ll nominator;//分子
	ll denominator;//分母
};

