import sys

from sklearn import tree
import math
from sklearn.metrics import classification_report
import pandas as pd

class myDecisionTree:
    def __init__(self, inTrainPath, inTestPath, maxDepth, outTrainLabelPath, outTestLabelPathm, outMetricsPath):
        self.inTrainPath = inTrainPath
        self.inTestPath = inTestPath

        self.outTrainLabelPath = outTrainLabelPath
        self.outTestLabelPath = outTestLabelPathm
        self.outMetricsPath = outMetricsPath

        self.maxDepth = maxDepth

        self.type = 0    # 0:small数据集，1:politician数据集，2:education数据集，3:mushrooms数据集
        self.features = []
        self.numToFeature = []    # 记录数字与属性值之间的映射关系
        self.numToLabel = []    # 记录数字与标签值之间的映射关系

    # 载入数据并做预处理
    def loadData(self, inPath):
        data = pd.read_csv(inPath, sep='\t', header=0)
        data = pd.DataFrame(data)

        x = data[data.keys()[: -1]]
        y = data[data.keys()[-1: ]]

        self.features = x.keys()

        # 手动构建数据集字母替换为数字的映射
        if len(self.numToFeature) == 0:
            if "n" in x.values and "democrat" in y.values:    # politicians
                self.numToFeature.append("n")
                self.numToFeature.append("y")
                self.numToLabel.append("democrat")
                self.numToLabel.append("republican")
            elif "notA" in x.values and "notA" in y.values:   # education
                self.numToFeature.append("notA")
                self.numToFeature.append("A")
                self.numToLabel.append("A")
                self.numToLabel.append("notA")
            else:                                             # mushroom
                self.numToFeature.append(0)
                self.numToFeature.append(1)
                self.numToLabel.append(0)
                self.numToLabel.append(1)

        # 数据集字母替换为数字
        for i in range(len(self.numToFeature)):
            x = x.replace(self.numToFeature[i], i)
        for i in range(len(self.numToLabel)):
            y = y.replace(self.numToLabel[i], i)

        return x, y

    # 构建决策树，maxDepth=0时使用
    def buildDecisionTree(self, xTrain, yTrain, maxDepth):
        model = tree.DecisionTreeClassifier(
            criterion="entropy",
            max_depth=maxDepth,
            min_impurity_decrease=1e-9
        )
        model = model.fit(xTrain, yTrain)
        return model

    # 打印决策树
    def printDecisionTree(self, decisionTree: tree.DecisionTreeClassifier, pre, now, depth):
        nodeCnt = decisionTree.tree_.node_count    # 结点数量
        childrenLeft = decisionTree.tree_.children_left    # 左子树
        childrenRight = decisionTree.tree_.children_right    # 右子树
        feature = decisionTree.tree_.feature    # 切分结点的属性
        value = decisionTree.tree_.value    # 各结点中各label的个数

        line = ""
        if pre != -1:
            line += "| " * depth + \
                    self.features[feature[pre]] + \
                    " = " + \
                    (str(self.numToFeature[0]) if childrenLeft[pre] == now else str(self.numToFeature[1])) + \
                    ": "
        line += "["
        for i in range(len(self.numToLabel)):
            num = int(value[now][0][i])
            line += str(num) + " " + str(self.numToLabel[i]) + ("/" if i != len(self.numToLabel) - 1 else "]")
        print(line)
        if now < nodeCnt and childrenLeft[now] != -1 and childrenRight[now] != -1:
            self.printDecisionTree(decisionTree, now, childrenRight[now], depth + 1)
            self.printDecisionTree(decisionTree, now, childrenLeft[now], depth + 1)

    # 打印Majority Vote Classifier分类器
    def printMajorVoteClassifier(self, yTrain):
        label = yTrain.keys()[-1]
        label = pd.value_counts(yTrain[label])
        line = "["
        for i in range(len(self.numToLabel)):
            line += str(label[i]) + " " + str(self.numToLabel[i]) + ("/" if i != len(self.numToLabel) - 1 else "]")
        print(line)

    # 处理预测值，相同概率标签选字典序大的
    def predict(self, decisionTree: tree.DecisionTreeClassifier, data):
        probas = decisionTree.predict_proba(data)
        predict = []
        for proba in probas:
            candidate = []
            for i in range(len(proba)):
                if math.isclose(proba[i], max(proba)):
                    candidate.append(self.numToLabel[i])
            predict.append(max(candidate))
        return predict

    # Majority Vote Classifier，max_depth=0时使用
    def majorVotePredict(self, yTrain, yTest):
        label = yTrain.keys()[-1]
        label = pd.value_counts(yTrain[label]).sort_index(ascending=False).sort_values(ascending=False).idxmax()
        return [self.numToLabel[label] for i in range(len(yTrain))], [self.numToLabel[label] for i in range(len(yTest))]

    def writePredictLabel(self, predict, outLabelPath):
        f = open(outLabelPath, "w")
        for label in predict:
            f.writelines(str(label) + "\n")
        f.close()

    def judgeMetrics(self, predict, label):
        label = label.values.reshape(-1)
        label = [self.numToLabel[label[i]] for i in range(len(label))]
        measure = classification_report(y_true=label, y_pred=predict, output_dict=True)
        measure = pd.DataFrame(measure).transpose()
        return 1 - measure["recall"]["accuracy"]

    def writeMetrics(self, trainPredict, testPredict, yTrain, yTest, outMetricsPath):
        trainError = self.judgeMetrics(trainPredict, yTrain)
        testError = self.judgeMetrics(testPredict, yTest)
        f = open(outMetricsPath, "w")
        f.write("error(train): " + str(trainError) + "\nerror(test): " + str(testError))
        f.close()

    def run(self):
        xTrain, yTrain = self.loadData(self.inTrainPath)
        xTest, yTest = self.loadData(self.inTestPath)
        if self.maxDepth > 0:
            decisionTree = self.buildDecisionTree(xTrain, yTrain, self.maxDepth)
            self.printDecisionTree(decisionTree, -1, 0, 0)
            trainPredict = self.predict(decisionTree, xTrain)
            testPredict = self.predict(decisionTree, xTest)
        else:
            trainPredict, testPredict = self.majorVotePredict(yTrain, yTest)
            self.printMajorVoteClassifier(yTrain)
        self.writePredictLabel(trainPredict, self.outTrainLabelPath)
        self.writePredictLabel(testPredict, self.outTestLabelPath)
        self.writeMetrics(trainPredict, testPredict, yTrain, yTest, self.outMetricsPath)


# myDecisionTree("handout/education_train.tsv", "handout/education_test.tsv", 3, "result/edu_3_train.labels", "result/edu_3_test.labels", "result/edu_3_metrics.txt").run()

def main():
    argv = sys.argv
    print(argv)
    if len(argv) == 7:
        myDecisionTree(argv[1], argv[2], int(argv[3]), argv[4], argv[5], argv[6]).run()


if __name__ == "__main__":
    main()






