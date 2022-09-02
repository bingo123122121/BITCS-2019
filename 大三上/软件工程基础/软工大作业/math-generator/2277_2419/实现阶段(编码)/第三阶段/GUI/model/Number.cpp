#include "Number.h"

Number::Number()
{
	nominator = 0;
	denominator = 1;
}
Number::Number(ll n1, ll n2)
{
	if (n2 == 0)
	{
		printf("分母不能为0！\n");
		nominator = 0x3f3f3f3f;
		denominator = 1;
	}
	else
	{
		nominator = n1;
		denominator = n2;
	}
}
ll Number::getNominator()
{
	return nominator;
}
ll Number::getDenominator()
{
	return denominator;
}
void Number::setNumber(ll nominator, ll denominator)
{
	if (denominator == 0)
	{
		printf("分母不能为0！\n");
		this->nominator = 0x3f3f3f3f;
		this->denominator = 1;
		return;
	}
	this->nominator = nominator;
	this->denominator = denominator;
}

bool Number::operator==(const Number& num)
{
	if (num.nominator == this->nominator && num.denominator == this->denominator)
	{
		return true;
	}
	return false;
}