// 题目生成模块

#include "../model/File.h"
#include "../model/Formula.h"
#pragma once
class ProblemGenerate
{
public:
	ProblemGenerate();
	~ProblemGenerate();
	void generate();

private:
	File* file;
	Formula* formula;

	static set<ll> hashValueSet;
};

