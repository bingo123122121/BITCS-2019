import time

# time
start = time.time()

# input
file = open("data.txt")
content = file.readlines()
n = int(content[0].strip("\n"))
matrix1 = []
matrix2 = []
matrix3 = []
for i in range(1, n + 1):
    data = content[i].strip("\n").split()
    matrix1.append(data)
for i in range(n + 2, 2 * n + 2):
    data = content[i].strip("\n").split()
    matrix2.append(data)

# calculate
for i in range(n):
    matrix3.append([])
    for j in range(n):
        temp = 0
        for k in range(n):
            temp += int(matrix1[i][k]) * int(matrix2[k][j])
        matrix3[i].append(temp)

# output
file.close()
file = open("result_python.txt", "w")
for i in range(n):
    for j in range(n):
        file.write(str(matrix3[i][j]) + ("\n" if j == n - 1 else " "))
file.close()

# time
end = time.time()
print("Total time:%.2fms" % ((end - start) * 1000))
