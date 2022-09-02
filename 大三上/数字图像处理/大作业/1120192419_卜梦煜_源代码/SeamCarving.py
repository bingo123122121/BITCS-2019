import FT
import cv2
import numpy as np
import matplotlib.pyplot as plt
from skimage import io

class SC:
    def __init__(self, filename, filename_out, out_height, out_width):
        self.filename = filename
        self.filename_out = filename_out
        self.out_height = out_height
        self.out_width = out_width

        self.temp = 0

        # 读入图片
        self.in_img = cv2.imread(filename).astype(np.float64)
        self.in_height, self.in_width = self.in_img.shape[0: 2]

        self.out_img = np.copy(self.in_img)

        self.run()


    def run(self):
        delta_height = self.out_height - self.in_height
        delta_width = self.out_width - self.in_width

        # 对列操作
        # 移除列
        if delta_width < 0:
            print("-----开始移除列-----")
            self.remove_seals(delta_width * -1)
            print("-----成功移除列-----")
        # 添加列
        elif delta_width > 0:
            print("-----开始添加列-----")
            self.insert_seals(delta_width)
            print("-----成功添加列-----")
            # plt.subplot(3, 2, 4)
            # plt.imshow(self.out_img[:, :, ::-1].astype(np.int))

        self.temp += 1

        # 对行操作，通过旋转转化成对列操作
        self.out_img = np.rot90(self.out_img, 1)
        # 移除行
        if delta_height < 0:
            print("-----开始移除行-----")
            self.remove_seals(delta_height * -1)
            print("-----成功移除行-----")
        # 添加行
        elif delta_height > 0:
            print("-----开始添加行-----")
            self.insert_seals(delta_height)
            print("-----成功添加行-----")
            # plt.subplot(3, 2, 4)
            # plt.imshow(np.rot90(self.out_img[:, :, ::-1].astype(np.int), -1))

        self.out_img = np.rot90(self.out_img, -1)

        self.save_img(self.filename_out)


    def remove_seals(self, num):
        for i in range(num):
            energy_map = self.get_energy()
            # if i == 0:
            #     plt.subplot(2, 2, 1)
            #     plt.imshow(np.rot90(energy_map, -1), 'gray')
            # if i == num - 1:
            #     plt.subplot(2, 2, 2)
            #     plt.imshow(np.rot90(energy_map, -1), 'gray')
            # print(energy_map)
            # print(np.max(energy_map))
            # cv2.imshow("img", energy_map)
            # cv2.waitKey(0)
            dp_map, back_path, seam = self.dp(energy_map)
            # print(dp_map)
            # print(back_path)
            self.remove_seam(dp_map, back_path)

    def insert_seals(self, num):
        temp_img = np.copy(self.out_img)
        remove_record = []

        for i in range(num):
            energy_map = self.get_energy()
            if i == num - 1 and self.temp == 0:
                plt.subplot(2, 2, 1)
                plt.imshow(np.rot90(energy_map, 0), 'gray')
                # float64转uint8
                io.imsave("gray_improved.jpg", (energy_map * 255).astype(np.uint8))
            dp_map, back_path, seam = self.dp(energy_map)
            remove_record.append(seam)
            self.remove_seam(dp_map, back_path)

        # plt.subplot(3, 2, self.temp+1)
        # plt.imshow(np.rot90(self.out_img[:, :, ::-1].astype(np.int), -1 if self.temp == 1 else 0))

        self.out_img = np.copy(temp_img)

        l = len(remove_record)
        for i in range(l):
            seam = remove_record.pop(0)

            self.add_seam(seam)
            remove_record = self.update_record(remove_record, seam)


    # 能量图 = 梯度图 + 视觉显著度图
    def get_energy(self):
        # 求梯度图
        x = cv2.convertScaleAbs(cv2.Sobel(self.out_img, cv2.CV_64F, 1, 0))
        y = cv2.convertScaleAbs(cv2.Sobel(self.out_img, cv2.CV_64F, 0, 1))
        grad_map = cv2.addWeighted(x, 0.5, y, 0.5, 0)
        grad_map = np.sum(grad_map, axis=2)

        if self.temp == 0:
            temp = (grad_map - np.min(grad_map)) / (np.max(grad_map) - np.min(grad_map))
            io.imsave("gray_origin.jpg", (temp * 255).astype(np.uint8))

        # 求视觉显著度图
        ft_map = FT.ft(self.out_img.astype(np.uint8))

        # 加权生成能量图
        alpha = 0.8
        energy_map = alpha * grad_map / np.max(grad_map) + (1 - alpha) * ft_map
        energy_map = (energy_map - np.min(energy_map)) / (np.max(energy_map) - np.min(energy_map))

        return energy_map

    # dp为竖向dp，获得最小能量路径
    def dp(self, energy_map):
        dp_map = np.copy(energy_map)
        back_path = np.zeros_like(dp_map, dtype=np.int)
        height, width = self.out_img.shape[0: 2]
        for i in range(1, height):
            for j in range(width):
                if j == 0:
                    temp = np.concatenate(([256], dp_map[i - 1, j: j + 2]))
                elif j == self.in_width - 1:
                    temp = dp_map[i - 1, j - 1: j + 1]
                else:
                    temp = dp_map[i - 1, j - 1: j + 2]

                min_index = np.argmin(temp)
                back_path[i, j] = min_index + j - 1
                dp_map[i, j] += dp_map[i - 1, min_index + j - 1]

        seam = np.zeros((height, ), dtype=np.int)
        j = np.argmin(energy_map[-1])
        for i in reversed(range(height)):
            seam[i] = j
            j = back_path[i, j]

        return dp_map, back_path, seam

    # 根据dp_map移除缝隙
    def remove_seam(self, energy_map, back_path):
        height, width = self.out_img.shape[0: 2]
        mask = np.ones((height, width), dtype=np.bool)
        j = np.argmin(energy_map[-1])
        for i in reversed(range(height)):
            mask[i, j] = False
            j = back_path[i, j]

        mask = np.stack([mask] * 3, axis=2)
        # print(mask.shape, self.out_img.shape)
        self.out_img = self.out_img[mask].reshape((height, width-1, 3))


    def add_seam(self, seam):
        height, width = self.out_img.shape[0: 2]
        img_temp = np.zeros((height, width + 1, 3))
        for i in range(height):
            col = seam[i]
            for k in range(3):
                if col == 0:
                    val = np.average(self.out_img[i, col: col + 2, k])
                else:
                    val = np.average(self.out_img[i, col - 1: col + 1, k])

                img_temp[i, : col, k] = self.out_img[i, : col, k]
                img_temp[i, col, k] = np.round(val).astype(int)
                img_temp[i, col + 1: , k] = self.out_img[i, col: , k]
        self.out_img = np.copy(img_temp)

    def update_record(self, rest_seams, seam):
        temp = []
        for i in rest_seams:
            i[np.where(i >= seam)] += 2
            temp.append(i)
        return temp

    def save_img(self, filename):
        print("-----展示图片-----")
        plt.subplot(2, 2, 3)
        plt.imshow(self.in_img[:, :, ::-1].astype(np.int))
        plt.subplot(2, 2, 4)
        plt.imshow(self.out_img[:, :, ::-1].astype(np.int))
        plt.show()
        cv2.imwrite(filename, self.out_img.astype(np.uint8))











