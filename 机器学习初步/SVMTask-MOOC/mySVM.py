from sklearn import svm
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split
from sklearn.metrics import classification_report
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

class MySVM:
    def __init__(self):
        self.inIrisPath = "./iris/iris.data"

    def labelConvert(self, dic):
        irisLabel = {b"Iris-setosa": 0, b"Iris-versicolor": 1, b"Iris-virginica": 2}
        return irisLabel[dic]

    def loadData(self, inIrisPath):
        data = np.loadtxt(inIrisPath, dtype=float, delimiter=',', converters={4: self.labelConvert})
        x, y = np.split(data, indices_or_sections=(4, ), axis=1)
        trainx, testx, trainy, testy = train_test_split(x, y, random_state=1, train_size=0.8, test_size=0.2)
        trainy = trainy.reshape(-1)
        testy = testy.reshape(-1)

        return trainx, testx, trainy, testy

    def mySVM(self, trainx, trainy, kernel, C, gamma):
        model = Pipeline([
            ("scaler", StandardScaler()),
            ("svm", svm.SVC(kernel=kernel, C=C, gamma=gamma, decision_function_shape="ovr"))
        ])
        model.fit(trainx, trainy)
        return model

    def judgeModel(self, model, trainx, testx, trainy, testy):
        trainPredict = model.predict(trainx)
        testPredict = model.predict(testx)
        targetName = ["class 1", "class 2", "class 3"]
        measureTrain = classification_report(trainPredict, trainy, target_names=targetName, output_dict=True)
        measureTest = classification_report(testPredict, testy, target_names=targetName, output_dict=True)

        dfTrain = pd.DataFrame(measureTrain).transpose()
        dfTest = pd.DataFrame(measureTest).transpose()

        return dfTrain["f1-score"]["accuracy"], dfTest["f1-score"]["accuracy"]

    def run(self):
        trainx, testx, trainy, testy = self.loadData(self.inIrisPath)

        recordTrain = []
        recordTest = []
        parameterC = [0.1, 1, 5]
        sizeC = len(parameterC)
        parameterGamma = [0.1, 1, 5, 10, 100]
        sizeGamma = len(parameterGamma)
        parameterKernel = ["rbf"]
        sizeKernel = len(parameterKernel)

        for C in parameterC:
            for gamma in parameterGamma:
                for kernel in parameterKernel:
                    model = self.mySVM(trainx, trainy, kernel, C, gamma)
                    scoreTrain, scoreTest = self.judgeModel(model, trainx, testx, trainy, testy)
                    recordTrain.append(scoreTrain)
                    recordTest.append(scoreTest)

        fig, ax = plt.subplots()

        # 对比kernel
        # ax.plot([t for t in range(sizeKernel)], recordTrain, label="train set")
        # ax.plot([t for t in range(sizeKernel)], recordTest, label="test set")
        # plt.xticks([t for t in range(sizeKernel)], parameterKernel)
        # ax.set_xlabel("kernel")
        # ax.set_ylabel("accuracy")
        # ax.set_title("C=5, gamma=5")

        for i in range(sizeC):
            ax.plot([t for t in range(sizeGamma)], recordTest[sizeGamma*i: sizeGamma*(i+1)], label="C="+str(parameterC[i]))
        plt.xticks([t for t in range(sizeGamma)], parameterGamma)
        ax.set_xlabel("gamma")
        ax.set_ylabel("accuracy")
        ax.set_title("test set")
        ax.legend()
        plt.show()




MySVM().run()