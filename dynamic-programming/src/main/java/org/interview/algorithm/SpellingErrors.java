package org.interview.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sam Ma
 * @date 2020/10/06
 * 使用动态规划算法实现搜索引擎中的拼写纠错功能
 */
public class SpellingErrors {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpellingErrors.class);

    private char[] mitcmu = "mitcmu".toCharArray();

    private char[] mtacnu = "mtacnu".toCharArray();

    private int mitcmu_length = 6;

    private int mtacnu_length = 6;

    /**
     * 用于存储最终计算结果
     */
    public int minDst = Integer.MAX_VALUE;

    /**
     * 编程计算拉文斯坦距离(使用回溯算法算法实现)
     *
     * @param i
     * @param j
     * @param edist
     */
    public void lwstBT(int i, int j, int edist) {
        if (i == mitcmu_length || j == mtacnu_length) {
            if (j < mitcmu_length) edist += (mitcmu_length - j);
            if (j < mtacnu_length) edist += (mtacnu_length - j);
            if (edist < minDst) minDst = edist;
            return;
        }
        // 当两个字符匹配的时候，直接比较两个字符串的下一个字符
        if (mitcmu[i] == mtacnu[j]) {
            lwstBT(i + 1, j + 1, edist);
        } else {
            // 删除mitcmu[i]或者mtacmu[j]前添加一个字符
            lwstBT(i + 1, j, edist + 1);
            // 删除mtacmu[j]或者mitcmu[i]前添加一个字符
            lwstBT(i, j + 1, edist + 1);
            // 将mitcmu[i]或者mtacmu[j]替换为相同的字符串
            lwstBT(i + 1, j + 1, edist + 1);
        }
    }

    /**
     * 使用动态规划算法计算莱文斯坦距离(状态二维数组最后一个元素存储的即为距离长度)
     *
     * @param mitcmu
     * @param mitLength
     * @param mtacnu
     * @param mtaLength
     */
    public int lwstDP(char[] mitcmu, int mitLength, char[] mtacnu, int mtaLength) {
        int[][] minDist = new int[mitLength][mtaLength];
        // 初始化第0行：mitcmu[0..0]与mtacnu[0..j]的编辑距离
        for (int j = 0; j < mtaLength; j++) {
            if (mitcmu[0] == mtacnu[j]) minDist[0][j] = j;
            else if (j != 0) minDist[0][j] = minDist[0][j - 1] + 1;
            else minDist[0][j] = 1;
        }
        // 初始化第0列: mitcmu[0..i]与mtacnu[0..0]的编辑距离
        for (int i = 0; i < mitLength; ++i) {
            if (mitcmu[i] == mtacnu[0]) minDist[i][0] = i;
            else if (i != 0) minDist[i][0] = minDist[i - 1][0] + 1;
            else minDist[i][0] = 1;
        }

        // 按行进行填表
        for (int i = 1; i < mitLength; ++i) {
            for (int j = 1; j < mtaLength; ++j) {
                if (mitcmu[i] == mtacnu[j]) minDist[i][j] = minValue(
                        minDist[i - 1][j] + 1, minDist[i][j - 1] + 1, minDist[i - 1][j - 1]);
                else minDist[i][j] = minValue(
                        minDist[i - 1][j] + 1, minDist[i][j - 1] + 1, minDist[i - 1][j - 1] + 1);
            }
        }

        return minDist[mitLength - 1][mtaLength - 1];
    }

    private int minValue(int x, int y, int z) {
        int minv = Integer.MAX_VALUE;
        if (x < minv) minv = x;
        if (y < minv) minv = y;
        if (z < minv) minv = z;
        return minv;
    }

    /*public static void main(String[] args) {
        SpellingErrors spellingErrors = new SpellingErrors();
        // 1.使用回溯算法针对mitcmu、mtacnu字符串进行计算，计算最短操作次数
//        spellingErrors.lwstBT(0, 0, 0);
//        LOGGER.info("Levenshtein distance between mitcmu and mtacnu is: [{}]", spellingErrors.minDst);

        // 2.使用动态规划计算字符串间的莱文斯坦距离
        int lwtDistance = spellingErrors.lwstDP("mitcmu".toCharArray(), 6, "mtacnu".toCharArray(), 6);
        LOGGER.info("Levenshtein distance between mitcmu and mtacnu using DP is: [{}]", lwtDistance);
    }*/

}
