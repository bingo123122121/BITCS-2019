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
	cout << "��ʼ������Ŀ..." << endl;
	for (int i = 0; i < 1000; i++)
	{
        //cout << formula->getStr() << endl;
			//��hashֵ�ж����ɵ�ʽ���Ƿ�Ϸ�
		while (hashValueSet.count(formula->getHash()))
		{
			//�����Ϸ�����������ʽ��
			formula->generate();
			//cout << formula->getHash() << " " << hashValueSet.count(formula->getHash()) << endl;
		}
		hashValueSet.insert(formula->getHash());
		file->writeToFile(formula);
		formula->generate();
	}
	cout << "������ɡ�" << endl;
}
