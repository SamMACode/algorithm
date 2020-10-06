package org.interview.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sam Ma
 * @date 2020/10/06
 * 背包问题（背包可状态物品重量固定、单个商品重量固定），求可装的最大商品; 动态规划具有: 最优子结构、无后效性、重复子问题的特征
 */
public class KnapsackProblem {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnapsackProblem.class);

    /**
     * 物品挑选结果放入到maxW中
     */
    private int maxW = Integer.MIN_VALUE;

    private int[] weights = {2, 2, 4, 6, 3};

    /**
     * 商品的总数量为5
     */
    private int number = 5;

    /**
     * 背包承受的最大重量为9
     */
    private int bagWeight = 9;

    /**
     * 备忘录（用于避免在递归中进行重复计算）
     */
    private boolean[][] memory = new boolean[5][10];

    /**
     * i表示决策第i个商品是否加入背包，cw表示背包中当前物品的总重量(使用回溯算法)
     *
     * @param i  决策当前的物品i
     * @param cw 当前背包的已装物品的重量
     */
    public void find(int i, int cw) {
        // cw == weight表示已经装满，i==n表示物品都已经考察完
        if (cw == bagWeight || i == number) {
            if (cw > maxW) maxW = cw;
            return;
        }
        // 若存在计算重复的状态，直接进行返回；否则，将memory中相应坐标标记为true
        if (memory[i][cw]) return;
        memory[i][cw] = true;

        // 选择对与第i个物品不进行装入(这块其实就是在枚举不同的情况：装入和不装入两种结果)
        find(i + 1, cw);
        if (cw + weights[i] <= bagWeight) {
            // 选择装入第i个物品
            find(i + 1, cw + weights[i]);
        }
    }

    /**
     * 使用动态规划解决背包问题(knapsack解决, 使用动态规划更好理解)
     *
     * @param weights   物品的重量
     * @param number    物品的数量
     * @param bagWeight 背包最大可承载的数量
     * @return
     */
    public int knapsack(int[] weights, int number, int bagWeight) {
        boolean[][] states = new boolean[number][bagWeight + 1];
        /*
         * 第一行数据要进行特殊处理，可以利用哨兵进行优化; states[0][0]为不放入背包情况，
         *  if条件判断为将元素放入bag的情况.
         */
        states[0][0] = true;
        if (weights[0] < bagWeight) {
            states[0][weights[0]] = true;
        }

        for (int i = 1; i < number; i++) {
            // 动态规划状态转移, 不将第i个物品放入到背包的情况
            for (int j = 0; j <= bagWeight; j++) {
                if (states[i - 1][j] == true) states[i][j] = states[i - 1][j];
            }
            // 把第i个物品放到到背包的情况
            for (int j = 0; j <= bagWeight - weights[i]; ++j) {
                if (states[i - 1][j] == true) states[i][j + weights[i]] = true;
            }
        }

        // 输出动态规划的计算结果(在states数组中从最后一行的最后列开始校验)，业即背包中物品总重量的最大值
        for (int i = bagWeight; i >= 0; --i) {
            if (states[number - 1][i] == true) return i;
        }
        return 0;
    }

    /**
     * 使用动态规划计算背包最大重量问题(内存空间优化，使用一纬数组存储结点状态)
     *
     * @param items
     * @param number
     * @param bagWeight
     * @return
     */
    public int knapsackTuning(int[] items, int number, int bagWeight) {
        // 一纬数组states的默认状态为false
        boolean[] states = new boolean[bagWeight + 1];
        /*
         * 对第一行的数据进行特殊处理，分别在states中进行标注(分情况讨论:取第一个物品或者不取)
         */
        states[0] = true;
        if (items[0] < bagWeight) {
            states[items[0]] = true;
        }

        // 动态规划实现逻辑，将第i个元素放入到背包中. 不放入背包的不需要再记录一次,for循环之前的逻辑已经记录(使用一纬数组)
        for (int i = 0; i < number; i++) {
            for (int j = bagWeight - items[i]; j >= 0; --j) {
                if (states[j] == true) states[j + items[i]] = true;
            }
        }

        for (int i = bagWeight; i >= 0; --i) {
            if (states[i] == true) return i;
        }
        return 0;
    }

    /*public static void main(String[] args) {
        // 1.使用回溯算法解决背包问题(枚举出所有可能的情况，从中计算出最优的值)
        KnapsackProblem knapsackProblem = new KnapsackProblem();
//        knapsackProblem.find(0, 0);
//        LOGGER.info("knapsackProblem final result is: [{}]", knapsackProblem.maxW);

        // 2.使用动态规划算法，从中获取得到背包中最大可装载物品的重量
        int[] weights = {2, 2, 4, 6, 3};
//        int dynamicValue = knapsackProblem.knapsack(weights, 5, 9);
//        LOGGER.info("dynamic programming value is: [{}]", dynamicValue);

        // 3.优化后的版本，将临时二维数组转换为使用一纬数组
        int dynamicValue = knapsackProblem.knapsackTuning(weights, 5, 9);
        LOGGER.info("KnapsackProblem with space tuning, final value: [{}]", dynamicValue);
    }*/

}
