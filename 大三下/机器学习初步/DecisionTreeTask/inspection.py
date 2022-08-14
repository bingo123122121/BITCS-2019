import pandas as pd
import numpy as np
import sys

class Inspection:
    def __init__(self, inTSVPath, outTSVPath):
        self.inTSVPath = inTSVPath
        self.outTXTPath = outTSVPath

    def preprocessTSV(self, inTSVPath: str):
        data = pd.read_csv(inTSVPath, sep='\t', header=0)
        data = pd.DataFrame(data)
        return data

    def calculateRootEntropy(self, data: pd.DataFrame):
        label = data.keys()[-1]
        rate = pd.value_counts(data[label]) / len(data[label])
        return sum(-1*rate*np.log2(rate))

    def calculateMajorityVoteErrorRate(self, data: pd.DataFrame):
        label = data.keys()[-1]
        rate = pd.value_counts(data[label]) / len(data[label])
        return 1 - rate.max()

    def writeResultFile(self, outTXTFile, rootEntropy, majorityVoteErrorRate):
        result = "entropy: " + str(rootEntropy) + "\nerror: " + str(majorityVoteErrorRate)
        f = open(outTXTFile, "w")
        f.writelines(result)

    def run(self):
        data = self.preprocessTSV(self.inTSVPath)
        rootEntropy = self.calculateRootEntropy(data)
        majorityVoteErrorRate = self.calculateMajorityVoteErrorRate(data)
        self.writeResultFile(self.outTXTPath, rootEntropy, majorityVoteErrorRate)

Inspection("handout/small_train.tsv", "small_inspection.txt").run()

# def main():
#     argv = sys.argv
#     print(argv)
#     if len(argv) == 3:
#         Inspection(argv[1], argv[2]).run()
#
# if __name__ == "__main__":
#     main()