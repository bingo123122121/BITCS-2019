#include "Calculater.h"
#include <cstdlib>
#include<math.h>

Calculater::Calculater()
{ }

ll Calculater::gcd(ll n1, ll n2)
{
	if (n1 == 0 || n1 % n2 == 0)
	{																												
		return n2;
	}
	return gcd(n2, n1 % n2);
}

Number Calculater::reduction(Number n)
{
	ll nominator = n.getNominator();
	ll denominator = n.getDenominator();
	ll gcdnum = gcd(abs(nominator), abs(denominator));//求最大公约数
	nominator /= gcdnum;
	denominator /= gcdnum;

	//符号单独处理，并保证若有负号则一定在分子
	if (nominator * denominator >= 0)
		return Number(abs(nominator), abs(denominator));
	else
		return Number(-abs(nominator), abs(denominator));
}

Number Calculater::add(Number num1,Number num2)
{
	ll nominator, denominator;

	//通分相加
	nominator = num1.getNominator() * num2.getDenominator() + num1.getDenominator() * num2.getNominator();//分子
	denominator = num1.getDenominator() * num2.getDenominator();//分母

	//约分
	Number result(nominator, denominator);
	result = reduction(result);	
	return result;
}

Number Calculater::minus(Number num1, Number num2)
{
	ll nominator, denominator;

	//通分相减
	nominator = num1.getNominator() * num2.getDenominator() - num1.getDenominator() * num2.getNominator();
	denominator = num1.getDenominator() * num2.getDenominator();

	//约分
	Number result(nominator, denominator);
	result = reduction(result);
	return result;
}

Number Calculater::mutiplate(Number num1, Number num2)
{
	ll nominator, denominator;

	//分子分母分别相乘
	nominator = num1.getNominator() * num2.getNominator();
	denominator = num1.getDenominator() * num2.getDenominator();

	//约分
	Number result(nominator, denominator);
	result = reduction(result);
	return result;
}

Number Calculater::divide(Number num1, Number num2)
{
	//检查除数是否合法
	if (num2.getNominator() == 0)
	{
		printf("除数不能为0！\n");
		return Number(0x3f3f3f3f, 1);
	}

	ll nominator, denominator;
	//两数相除，相当于乘除数的倒数
	nominator = num1.getNominator() * num2.getDenominator();
	denominator = num1.getDenominator() * num2.getNominator();
	
	//约分
	Number result(nominator, denominator);
	result = reduction(result);
	return result;
}

Number Calculater::power(Number num1, Number num2)
{
	//检查指数和底数是否合法
	Number n1 = reduction(num1);
	Number n2 = reduction(num2);
	if ((n1.getNominator() == 0 && n2.getNominator() == 0) || n2.getDenominator() != 1)
	{
		printf("乘方运算不合法！\n");
		return Number(0x3f3f3f3f, 1);
	}
	ll nominator, denominator;
	//乘方运算
	if (n2.getNominator() >= 0)
	{
		nominator = pow(n1.getNominator(), n2.getNominator());
		denominator = pow(n1.getDenominator(), n2.getNominator());
	}
	else
	{
		nominator = pow(n1.getDenominator(), -n2.getNominator());
		denominator = pow(n1.getNominator(), -n2.getNominator());
	}

	//约分
	Number result(nominator, denominator);
	result = reduction(result);
	return result;
}
