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
	ll gcdnum = gcd(abs(nominator), abs(denominator));//�����Լ��
	nominator /= gcdnum;
	denominator /= gcdnum;

	//���ŵ�����������֤���и�����һ���ڷ���
	if (nominator * denominator >= 0)
		return Number(abs(nominator), abs(denominator));
	else
		return Number(-abs(nominator), abs(denominator));
}

Number Calculater::add(Number num1,Number num2)
{
	ll nominator, denominator;

	//ͨ�����
	nominator = num1.getNominator() * num2.getDenominator() + num1.getDenominator() * num2.getNominator();//����
	denominator = num1.getDenominator() * num2.getDenominator();//��ĸ

	//Լ��
	Number result(nominator, denominator);
	result = reduction(result);	
	return result;
}

Number Calculater::minus(Number num1, Number num2)
{
	ll nominator, denominator;

	//ͨ�����
	nominator = num1.getNominator() * num2.getDenominator() - num1.getDenominator() * num2.getNominator();
	denominator = num1.getDenominator() * num2.getDenominator();

	//Լ��
	Number result(nominator, denominator);
	result = reduction(result);
	return result;
}

Number Calculater::mutiplate(Number num1, Number num2)
{
	ll nominator, denominator;

	//���ӷ�ĸ�ֱ����
	nominator = num1.getNominator() * num2.getNominator();
	denominator = num1.getDenominator() * num2.getDenominator();

	//Լ��
	Number result(nominator, denominator);
	result = reduction(result);
	return result;
}

Number Calculater::divide(Number num1, Number num2)
{
	//�������Ƿ�Ϸ�
	if (num2.getNominator() == 0)
	{
		printf("��������Ϊ0��\n");
		return Number(0x3f3f3f3f, 1);
	}

	ll nominator, denominator;
	//����������൱�ڳ˳����ĵ���
	nominator = num1.getNominator() * num2.getDenominator();
	denominator = num1.getDenominator() * num2.getNominator();
	
	//Լ��
	Number result(nominator, denominator);
	result = reduction(result);
	return result;
}
