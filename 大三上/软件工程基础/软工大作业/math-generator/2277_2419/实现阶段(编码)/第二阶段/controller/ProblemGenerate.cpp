#include "ProblemGenerate.h"

set<ll> ProblemGenerate::hashValueSet;

ProblemGenerate::ProblemGenerate()
{
	file = new File();
	formula = new Formula();
	generate();
}

ProblemGenerate::ProblemGenerate(bool powerFlag)
{
	file = new File();
	formula = new Formula(powerFlag);
	generate();
}

ProblemGenerate::~ProblemGenerate()
{
	delete file;
	delete formula;
}

void ProblemGenerate::generate()
{
	hashValueSet.clear();
	file->removeFile();
	cout << "开始生成题目..." << endl;
	for (int i = 0; i < 1000; i++)
	{
        //cout << formula->getStr() << endl;
			//用hash值判断生成的式子是否合法
		while (hashValueSet.count(formula->getHash()))
		{
			//若不合法，重新生成式子
			formula->generate();
			//cout << formula->getHash() << " " << hashValueSet.count(formula->getHash()) << endl;
		}
		hashValueSet.insert(formula->getHash());
		file->writeToFile(formula);
		formula->generate();
	}
	cout << "生成完成。" << endl;
}
