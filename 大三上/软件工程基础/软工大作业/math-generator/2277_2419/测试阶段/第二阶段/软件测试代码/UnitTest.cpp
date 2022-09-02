#include "pch.h"
#include "CppUnitTest.h"
#include "../四则运算系统/model/Number.h"
#include "../四则运算系统/model/Node.h"
#include "../四则运算系统/model/Formula.h"
#include "../四则运算系统/model/Calculater.h"
#include "../四则运算系统/model/File.h"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace UnitTest
{
	TEST_CLASS(UnitTest)
	{
	public:
		
		// 测试Number类
		TEST_METHOD(testNumber)
		{
			Number num1 = Number();
			Number num2 = Number(1, 2);
			Number num3 = Number(1, 0);

			num1.setNumber(1, 0);
			num1.setNumber(1, 2);

			int a = num1.getNominator();
			int b = num1.getDenominator();

			bool t1 = (Number(1, 2) == Number(1, 2));
			bool t2 = (Number() == Number(1, 2));
		}


		// 测试Node类
		TEST_METHOD(testNode)
		{
			Node n1 = Node();
			Node n2 = Node(true);
			Node n3 = Node(false);

			Number num = Number();
			n1.setData(num);
			n1.setData('+');

			nodeData n = n1.getData();
			bool f = n1.getType();
		}


		// 测试Calculater类
		TEST_METHOD(testCalculater)
		{
			Calculater calculater = Calculater();

			Number num1 = Number(2, 4);
			Number num2 = Number(4, 5);

			num1 = calculater.reduction(num1);

			calculater.add(num1, num2);

			calculater.minus(num1, num2);

			calculater.mutiplate(num1, num2);

			calculater.divide(num1, num2);
			calculater.divide(num1, Number());

			calculater.power(num1, num2);
		}


		// 测试Formula类
		TEST_METHOD(testFormula)
		{
			Formula formula1 = Formula(false);
			for (int i = 0; i < 10; i++)
			{
				Formula formula = Formula();

				formula.getStr();

				formula.getValue();

				formula.getHash();
			}
		}

		// 测试File类
		TEST_METHOD(testFile)
		{
			File* file = new File();

			file->setProblemPath("problems.txt");

			file->removeFile();

			Formula* formula = new Formula();

			for (int i = 0; i < 1000; i++)
			{
				formula->generate();
				file->writeToFile(formula);
			}

			file->readProblem(50);
			file->readProblem(-1);

			delete file;

		}
	};
}
