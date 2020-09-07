package org.interview.algorithm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Sam Ma
 * @date 2020/09/06
 * 冒泡排序算法的实现（冒泡排序会操作相邻的两个数据，一次冒泡会让至少一个元素移动到它应该在的位置）
 */
public class BubbleSort {

    private static final Logger logger = LoggerFactory.getLogger(BubbleSort.class);

    /**
     * 对数组使用冒泡排序算法(每次通过冒泡的方式将最大元素放到最后)
     *
     * @param array  要排序的数组
     * @param length 数组元素长度
     */
    public static void bubbleSort(int[] array, int length) {
        if (length <= 1) return;
        for (int i = 0; i < length; ++i) {
            /*
             * 提前退出冒泡排序的标志，若在本次冒泡过程中已无元素交换，则完成冒泡排序（外层for循环定义冒泡的次数）
             */
            boolean flag = false;
            for (int j = 0; j < length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    flag = true;
                }
            }
            // 若没有数据交换flag=false，则提前退出冒泡程序
            if (!flag) break;
        }
    }

/*    public static void main(String[] args) {
        int[] array = new int[]{4, 5, 6, 3, 2, 1};
        BubbleSort.bubbleSort(array, array.length);
        // 0    [main] INFO  org.interview.algorithm.impl.BubbleSort  - all array data after sorting is: [1, 2, 3, 4, 5, 6]
        logger.info("all array data after sorting is: {}", Arrays.toString(array));
    }*/

}
