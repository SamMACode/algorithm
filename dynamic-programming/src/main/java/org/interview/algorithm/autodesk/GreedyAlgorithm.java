package org.interview.algorithm.autodesk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sam Ma
 * @date 2021/02/07
 * 极客时间-动态规划专栏：01 硬币找零问题：从贪心算法说起
 * 问题：问题：给定 n 种不同面值的硬币，分别记为 c[0], c[1], c[2], … c[n]，同时还有一个总金额 k，编写一个函数计算出最少需要几枚硬币凑出这个金额 k？
 * 每种硬币的个数不限，且如果没有任何一种硬币组合能组成总金额时，返回 -1。
 */
public class GreedyAlgorithm {

    private static final Logger logger = LoggerFactory.getLogger(GreedyAlgorithm.class);

    /**
     * 使用贪心算法计算所需coin的数量
     *
     * @param total
     * @param values
     * @param valueCount
     * @return
     */
    private static int getMinCoinCountHelper(int total, int[] values, int valueCount) {
        int rest = total;
        int count = 0;

        // 从大到小遍历所有面值
        for (int i = 0; i < valueCount; ++i) {
            // 计算当前面值能用多少个
            int currentCount = rest / values[i];
            // 计算使用完当前面值后的余额
            rest = total - currentCount * values[i];
            // 增加当前的面额用量
            count += currentCount;

            if (rest == 0) {
                return count;
            }
        }
        // 执行到这里说明无法凑出总价，返回-1
        return -1;
    }

    /**
     * 使用回溯的方式计算最小数量
     *
     * @param total
     * @param values     币种的面值
     * @param valueIndex
     * @return
     */
    public static int getMinCountOfValue(int total, int[] values, int valueIndex) {
        int valueCount = values.length;
        if (valueIndex == valueCount) {
            return Integer.MAX_VALUE;
        }

        int minResult = Integer.MAX_VALUE;
        int currentValue = values[valueIndex];
        int maxCount = total / currentValue;

        for (int count = maxCount; count >= 0; count--) {
            int rest = total - count * currentValue;

            /* 如果rest为0，表示余额已经用尽，组合完成 */
            if (rest == 0) {
                minResult = Math.min(minResult, count);
                break;
            }
            // 否则尝试使用剩余面值求当前余额的硬币总数
            int restCount = getMinCountOfValue(rest, values, valueIndex + 1);
            // 后续没有可用组合，当前面值已经为0，返回-1表示尝试
            if (restCount == Integer.MAX_VALUE) {
                if (count == 0) {
                    break;
                }
                // 否则尝试把当前值-1
                continue;
            }
            minResult = Math.min(minResult, count + restCount);
        }

        return minResult;
    }

    /**
     *
     * @param total
     * @param values
     * @param k
     * @return
     */
    public static int getMinCoinCountLoop(int total, int[] values, int k) {
        int minCount = Integer.MAX_VALUE;
        int valueCount = values.length;

        if (k == valueCount) {
            return Math.min(minCount, getMinCountOfValue(total, values, 0));
        }

        for (int i = k; i <= valueCount - 1; ++i) {
            /* k位置已经排列好 */
            int t = values[k];
            values[k] = values[i];
            values[i] = t;
            minCount = Math.min(minCount, getMinCoinCountLoop(total, values, k + 1));  // 考虑后一位

            // 回溯
            t = values[k];
            values[k] = values[i];
            values[i] = t;
        }
        return minCount;
    }

     /*public static void main(String[] args) {*/
        // values内容位可使用的硬币面值，total为要置换的总数
        /*int[] values = {5, 3};
        int total = 11;
        int matched = getMinCoinCountHelper(total, values, 2);
        logger.info("getMinCoinCountHelper(total, values, 2) return: {}", matched);*/

        /*int[] values = {5, 3};
        int total = 11;
        int minCoin = getMinCoinCountLoop(total, values, 0);
        logger.info("(minCoin == INT_MAX) ? -1 : minCoin, values: [{}]", (minCoin == Integer.MAX_VALUE) ? -1 : minCoin);
    }*/

}
