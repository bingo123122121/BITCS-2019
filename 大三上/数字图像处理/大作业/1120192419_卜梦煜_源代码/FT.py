import cv2
import matplotlib.pyplot as plt
import numpy as np
from skimage import io


def ft(img):
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

    # 5*5高斯平滑
    img_gaussian = cv2.GaussianBlur(img, (5, 5), 0)

    # 转换色彩空间
    img_lab = cv2.cvtColor(img_gaussian, cv2.COLOR_RGB2LAB)

    # 分别计算l、a、b通道的均值
    (l, a, b) = cv2.split(img_lab)
    l_mean = np.mean(l)
    a_mean = np.mean(a)
    b_mean = np.mean(b)

    # 计算每个像素的lab值同lab均值的欧氏距离
    lab = np.square(img_lab - np.array([l_mean, a_mean, b_mean]))
    lab = np.sum(lab, axis=2)
    lab = lab / np.max(lab)

    # 展示图像
    # plt.imshow(lab,cmap='gray')
    # plt.show()

    return lab

# 使用示例
# imgPath = ""
# gray = ft(imgPath)

# float64转uint8
# io.imsave("result.jpg", (gray*255).astype(np.uint8))
# plt.imshow(gray)
# plt.show()