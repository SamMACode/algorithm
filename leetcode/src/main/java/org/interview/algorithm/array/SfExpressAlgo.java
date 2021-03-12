package org.interview.algorithm.array;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sam Ma
 * @date 2021/03/12
 * 顺丰同城面试-在线algorithm算法题 (求两个有序数组的中位数)
 */
public class SfExpressAlgo {

    private static final Logger logger = LoggerFactory.getLogger(SfExpressAlgo.class);

    /* 题目: 1. [1、3、5] [2、4、6], 下标为2  求两个有序数组的中位数, 另一种情况 [1、3、5]、 [2、4] 下标为2 */

    /**
     * 求两个有序数组的中位数（奇数取中间、偶数取两个平均）
     *
     * @param arrayA
     * @param arrayB
     * @return
     */
    public float getMidNumber(int[] arrayA, int[] arrayB) {
        /* 思路: 首先统计下arrayA和arrayB数组中元素总数，以此来计算中位数出现的位置 */
        int itemSize = arrayA.length + arrayB.length;
        boolean isOdd = false;
        int itemIndex = 0;

        /* 若数组元素数量刚好是2的倍数，则取排序后中间位置的两个数，并求平均数值; 若元素数量为奇数时，则取中间位置的元素作为中位数; */
        if (itemSize % 2 == 0) {
            isOdd = true;
            itemIndex = (itemSize / 2) - 1;
        } else {
            itemIndex = itemSize / 2;
        }

        // pointer用于指向有序数组中的元素，count用于统计累加次数
        int midValue = 0;
        int count = 0;

        // 使用for循环对两个有序数组中元素进行比较，并找到匹配的元素
        int indexA = 0;
        int indexB = 0;

        /*
         *  [1、3^、5]  arrayA
         *  [2、4^、6]  arrayB
         */
        /* 使用for循环从有序数组原地开始查找匹配的元素 */
        for (; indexA < arrayA.length && indexB < arrayB.length; count++) {
            // 数组从小到大进行排序，pointer优先指向较小元素
            if (arrayA[indexA] < arrayB[indexB]) {
                midValue = arrayA[indexA];
                indexA++;
            } else {
                midValue = arrayB[indexB];
                indexB++;
            }

            // 如果此时count数值等于itemIndex, 则说明找到了中间位置元素
            if (count == itemIndex) {
                break;
            }
        }

        if (isOdd) {
            // 在odd情况下，取midValue以及接下来的这个元素
            int another = -1;
            if (arrayA[indexA] >= arrayB[indexB]) {
                another = arrayB[indexB];
            } else {
                another = arrayA[indexA];
            }
            return (midValue + another) / 2.0f;
        } else {
            return midValue;
        }
        // isOdd = true;
        // 当数组中存在一个长、另外一个短时，也就是未能在for循环中找到中间元素，则需要从未遍历的部分取中间元素
        /*if (count != itemIndex) {

        }*/
//        return -1;
    }

    /*public static void main(String[] args) {
        SfExpressAlgo sfAlgo = new SfExpressAlgo();
        int[] arrayA = new int[]{1, 3, 5};
        int[] arrayB = new int[]{2, 4, 6};
        float midValue = sfAlgo.getMidNumber(arrayA, arrayB);
        // value between {1, 3, 5} and {2, 4, 6} is: [3.5]
        System.out.println("middle value between {1, 3, 5} and {2, 4, 6} is: [" + midValue + "]");

        // 另一个测试用例，当其中一个为偶数个数，另一个为奇数倍数
        int[] arrayC = new int[]{2, 4};
        midValue = sfAlgo.getMidNumber(arrayA, arrayC);
        // value between {1, 3, 5} and {2, 4} is: [3.0]
        System.out.println("middle value between {1, 3, 5} and {2, 4} is: [" + midValue + "]");
    }*/

}
