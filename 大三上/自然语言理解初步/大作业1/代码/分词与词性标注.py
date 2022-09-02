import math
import sys
import time
from timeit import default_timer as timer

A = {}    # 词性转移矩阵
B = {}    # 词性频度表
C = {}    # 词语/词性频度表
D = {}    # 词性出现在句首的频度
E = []    # 词性种类

MINN = -1e50

wfreq = {}    # 一元词频
bifreq = {}    # 二元词频

# 原始语料统计ABCDE
def preprocess1(trainSet):
    pre = [0, 0]
    for lines in trainSet:
        line = lines.replace(" [", "").replace("] ", "")
        line = line[:-1].split()
        for i in range(len(line)):
            pair = line[i].split("/")
            if len(pair) == 1 or pair[1] == "":
                continue

            # 词性种类
            if pair[1] not in E:
                E.append(pair[1])

            # 词性出现在句首的频度
            if i == 0:
                if pair[1] not in D.keys():
                    D[pair[1]] = 0
                D[pair[1]] += 1

            # 词性频度表
            if pair[1] not in B.keys():
                B[pair[1]] = 0
            B[pair[1]] += 1

            # 词语/词性频度表
            if pair[0] not in C.keys():
                C[pair[0]] = {}
            if pair[1] not in C[pair[0]].keys():
                C[pair[0]][pair[1]] = 0
            C[pair[0]][pair[1]] += 1

            # 统计词性转移矩阵
            if i != 0:
                if pre[1] not in A.keys():
                    A[pre[1]] = {}
                if pair[1] not in A[pre[1]].keys():
                    A[pre[1]][pair[1]] = 0
                A[pre[1]][pair[1]] += 1
            pre[0] = pair[0]
            pre[1] = pair[1]

# 原始语料去词性，将句子处理成词列表，包括带词性的set和不带词性的testSet，用于词性标注及其正确率检测
def preprocess2(set, testSet):
    for i in range(len(set)):
        set[i] = set[i].replace(" [", "").replace("] ", "")[:-1].split()
        line = set[i]
        sentence = []
        for i in range(len(line)):
            pair = line[i].split("/")
            if len(pair) == 1 or pair[1] == "":
                continue
            sentence.append(pair[0])
        testSet.append(sentence)

# 对词频与相邻两个词的词频做统计
def preprocess3(trainSet):
    wfreq["BOS"] = wfreq["EOS"] = 0
    for sent in trainSet:
        wfreq["EOS"] += 1
        wfreq["BOS"] += 1
        for i in range(len(sent)):
            # 处理unigram
            if sent[i] in wfreq.keys():
                wfreq[sent[i]] += 1
            else:
                wfreq[sent[i]] = 1
            # 处理bigram
            if i == 0:
                token = "BOS & " + sent[i]
            else:
                token = sent[i-1] + " & " + sent[i]
            if token in bifreq.keys():
                bifreq[token] += 1
            else:
                bifreq[token] = 1
            if i == len(sent) - 1:
                token = sent[i] + " & EOS"
                if token in bifreq.keys():
                    bifreq[token] += 1
                else:
                    bifreq[token] = 1

# 将set中词列表的词拼接成完整句子testSet，用于分词测试
def preprogress4(set, testSet):
    for i in range(len(set)):
        sent = ""
        for ci in set[i]:
            sent += ci
        testSet.append(sent)

# 对sentence中所有可能出现的词做统计，放在列表ci中
def cut(wfreq, sentence, ci):
    for i in range(len(sentence)):
        if sentence[i] not in ci:
            ci.append(sentence[i])
        for j in range(i+2, len(sentence)):
            if sentence[i: j] in wfreq.keys() and sentence[i: j] not in ci:
                ci.append(sentence[i: j])

# 分词viterbi算法
def viterbiFC(wfreq, bifreq, sentence, ci, result1, result):    # sentence为原始句子，ci为句中所有可能的词
    vit = [-5e100 for i in range(len(sentence))]
    pre = [0 for i in range(len(sentence))]
    prepos = [0 for i in range(len(sentence))]
    now = [0 for i in range(len(sentence))]
    for i in range(len(sentence)):
        for item in ci:
            if item[-1] == sentence[i] and len(item) <= i + 1 and item == sentence[i-len(item)+1: i+1]:
                if len(item) == i + 1:
                    if ("BOS & "+item) not in bifreq.keys():
                        nfreq = math.log10(1 / (wfreq["BOS"] + len(wfreq.keys())))
                    else:
                        nfreq = math.log10((bifreq["BOS & "+item] + 1) / (wfreq["BOS"] + len(wfreq.keys())))
                else:
                    if (str(now[i - len(item)]) + " & " + item) not in bifreq.keys():
                        if now[i - len(item)] not in wfreq.keys():
                            nfreq = MINN
                        else:
                            nfreq = math.log10(1 / (wfreq[now[i - len(item)]] + len(wfreq.keys())))
                    else:
                        nfreq = math.log10((bifreq[str(now[i-len(item)]) + " & " + item] + 1) /
                                           (wfreq[now[i - len(item)]] + len(wfreq.keys())))
                    nfreq += vit[i - len(item)]
                if i == len(sentence) - 1:
                    if (str(item) + " & EOS") not in bifreq.keys():
                        if item not in wfreq.keys():
                            nfreq += MINN
                        else:
                            nfreq += math.log10(1 / (wfreq[item] + len(wfreq.keys())))
                    else:
                        nfreq += math.log10((bifreq[item + " & EOS"] + 1) /
                                            (wfreq[item] + len(wfreq.keys())))
                if nfreq > vit[i]:
                    prepos[i] = i - len(item)
                    pre[i] = now[i-len(item)] if len(item) < i + 1 else "BOS"
                    now[i] = item
                    vit[i] = nfreq
    # 回溯记录分词结果
    result2 = []
    result2.append(len(sentence)-1)
    temp = len(sentence)-1
    while pre[temp] != "BOS":
        temp = prepos[temp]
        result2.append(temp)
    result2.append(-1)
    # print(result1[::-1])
    for i in range(1, len(result2)):
        result1.append([result2[len(result2)-i]+2, result2[len(result2)-i-1]+1])
        result.append(sentence[result2[len(result2)-i]+1: result2[len(result2)-i-1]+1])

# 词性标注viterbi
def viterbiCXBZ(ci, result):
    vit = {}    # vit[词序号][词性]：当前词为某种词性的概率
    pre = {}    # pre[词序号][词性]：当前词为某种词性是前一个词最大概率的词性
    # print(ci)
    for i in range(len(ci)):
        vit[i] = {}
        pre[i] = {}
        if ci[i] not in C.keys():
            C[ci[i]] = {}
            for type in E:
                C[ci[i]][type] = 1e-10
        for type in C[ci[i]].keys():
            vit[i][type] = -1e50
            pre[i][type] = (i - 1) if i != 0 else 0
    for i in range(len(ci)):
        if i == 0:
            for type in C[ci[i]].keys():
                if type not in D.keys():
                    D[type] = 1
                freq = math.log10(C[ci[i]][type] * D[type])
                vit[0][type] = freq
        else:
            for type2 in C[ci[i]].keys():
                freq = -1e100
                preType = 0
                for type1 in C[ci[i-1]].keys():
                    # print(ci[i-1], type1, ci[i], type2)
                    if type2 not in A[type1].keys():
                        A[type1][type2] = 1
                    freq1 = vit[i-1][type1] + math.log10(A[type1][type2] / B[type1] * C[ci[i]][type2] / B[type2])
                    if freq1 > freq:
                        freq = freq1
                        preType = type1
                vit[i][type2] = freq
                pre[i][type2] = preType
    temp = len(ci) - 1
    while temp >= 0:
        maxx = -5e100
        record = 0
        for type in vit[temp].keys():
            if vit[temp][type] > maxx:
                maxx = vit[temp][type]
                record = type
        result.append(ci[temp] + "/" + record)
        temp -= 1
    result = result.reverse()

# 计算单个句子的准确率，可共用
def calAccuracyForSentence(predict, ans):
    sum = 0
    for item in ans:
        if item in predict:
            sum += 1
    return sum, len(ans), len(predict)

def fenCi(testSet, ans, result):
    f1 = f2 = f3 = 0  # precise, recall, f1_score
    for i in range(len(testSet)):
        ci = []
        result1 = []
        # print(testSet[i])
        cut(wfreq, testSet[i], ci)
        # print(ci)
        viterbiFC(wfreq, bifreq, testSet[i], ci, result1, result[i])
        # print(result[i])
        # print(result)
        # print(ans[i])
        t1, t2, t3 = calAccuracyForSentence(result[i], ans[i])
        # print(t1, t2, t3)
        # print("\n")
        f1 += t1
        f2 += t2
        f3 += t3
    precise = f1 / f3
    recall = f1 / f2
    f1_score = 2 * precise * recall / (precise + recall)
    return precise, recall, f1_score

def CXBZ(testSet, ans):
    f1 = f2 = f3 = 0
    resultCXBZ = [[] for i in range(len(testSet))]
    for i in range(len(testSet)):
        viterbiCXBZ(testSet[i], resultCXBZ[i])
        t1, t2, t3 = calAccuracyForSentence(resultCXBZ[i], ans[i])
        # print(t1, t2, t3)
        f1 += t1
        f2 += t2
        f3 += t3
    precise = f1 / f3
    recall = f1 / f2
    f1_score = 2 * precise * recall / (precise + recall)
    return precise, recall, f1_score

def func():
    f = open("all.txt", encoding="utf-8")
    f = f.readlines()

    start1 = timer()

    trainSet = f[: 10000]
    ansCXBZ = f[10000: 12000]
    ansFC = []    # 分词的正确结果
    testSetCXBZ = [[] for i in range(len(ansCXBZ))]    # 词性标注测试集，由分词产生
    testSetFC = []    # 分词测试集
    trainSetFC = []    # 分词训练集

    # 预处理模块
    preprocess1(trainSet)
    preprocess2(ansCXBZ, ansFC)
    preprocess2(trainSet, trainSetFC)
    preprocess3(trainSetFC)
    preprogress4(ansFC, testSetFC)

    # 分词
    start = timer()
    precise, recall, f1_score = fenCi(testSetFC, ansFC, testSetCXBZ)
    end = timer()
    efficiency = sys.getsizeof(f[10000: 12000])/(end-start)/1024
    print("precise: " + str(precise) + ", recall: " + str(recall) + ", f1_score: " + str(f1_score) + ", efficiency: " + str(efficiency) + "KB/s")


    # 词性标注
    precise, recall, f1_score = CXBZ(testSetCXBZ, ansCXBZ)
    end1 = timer()
    efficiency = sys.getsizeof(f[10000: 12000]) / (end1 - start1) / 1024
    print("precise: " + str(precise) + ", recall: " + str(recall) + ", f1_score: " + str(f1_score) + ", efficiency: " + str(efficiency) + "KB/s")

func()


