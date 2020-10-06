package org.interview.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sam Ma
 * @date 2020/10/06
 * 使用动态规划、回溯算法解决最短路径问题(二维数组从左上角-->右下角，每经过一个节点累加距离)
 */
public class MinDistProblem {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinDistProblem.class);

    /**
     * 全局变量或者成员变量
     */
    private int minDist = Integer.MAX_VALUE;

    /**
     * 调用方式：minDistBacktracing(0, 0, 0, w, n)
     *
     * @param idx
     * @param jdx
     * @param dist
     * @param data
     * @param length 表示二维数组长度(length * length)
     */
    public void minDistBT(int idx, int jdx, int dist, int[][] data, int length) {
        dist += data[idx][jdx];
        // 到达了n-1这个位置了，需要进行举例子说明
        if (idx == length - 1 && jdx == length - 1) {
            if (dist < minDist) minDist = dist;
            return;
        }
        // 往下走，更新i = i + 1, j = j
        if (idx < length - 1) {
            minDistBT(idx + 1, jdx, dist, data, length);
        }
        // 往上走，更新i = i, j = j + 1
        if (jdx < length - 1) {
            minDistBT(idx, jdx + 1, dist, data, length);
        }
    }

    /**
     * 使用动态规划算法查找(0, 0) to (3, 3)的最短路径
     *
     * @param matrix
     * @param length
     * @return
     */
    public int minDistDP(int[][] matrix, int length) {
        int[][] states = new int[length][length];
        int sum = 0;
        // 初始化states数组的第一行数据，从(0, 0)开始累加直到(length, 0)位置
        for (int j = 0; j < length; ++j) {
            sum += matrix[0][j];
            states[0][j] = sum;
        }
        sum = 0;
        // 初始化第一列数据，从(0, 0)开始累加直到(0 ,length)位置
        for (int i = 0; i < length; ++i) {
            sum += matrix[i][0];
            states[i][0] = sum;
        }

        for (int i = 1; i < length; i++) {
            for (int j = 1; j < length; j++) {
                // 每一个单元格数据走向是从两方面来的，一种从states[i][j - 1]、另外一种从states[i - 1][j], 取最小值即可
                states[i][j] = matrix[i][j] + Math.min(states[i][j - 1], states[i - 1][j]);
            }
        }
        return states[length - 1][length - 1];
    }

    /*public static void main(String[] args) {
        int[][] matrix = {{1, 3, 5, 9}, {2, 1, 3, 4}, {5, 2, 6, 7}, {6, 8, 4, 3}};
        MinDistProblem minDistance = new MinDistProblem();
        minDistance.minDistBT(0, 0, 0, matrix, 4);
        // 使用回溯算法来计算从(0, 0)位置->(3, 3)位置的最短距离
        LOGGER.info("shortest path from (0, 0) to (3, 3) is: [{}]", minDistance.minDist);

        // 使用动态规划计算从(0, 0)位置->(3, 3)位置的最短距离
        Integer distance = minDistance.minDistDP(matrix, 4);
        LOGGER.info("shortest path from (0, 0) to (3, 3) use dynamic programming is: [{}]", distance);
    }*/

}
