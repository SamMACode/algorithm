package org.interview.algorithm.impl;

import org.interview.algorithm.AbstractSort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * InsertionSort插入排序算法的实现
 */
public class InsertionSort extends AbstractSort {

    private static final Logger logger = LoggerFactory.getLogger(InsertionSort.class);

    /**
     * algorithm 4中插入排序的实现
     * @param array
     */
    @Override
    public void sort(Comparable[] array) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            // 将array[i]插入到a[i-1]/a[i-2]/a[i-3]..之中,确保的是i之前的数组序列是完全有序地
            for (int j = i; j > 0 && less(array[j], array[j - 1]); j--) {
                // 对数组array中的元素进行排序,当存在array[j]<array[j-1]的情况,则对其进行交换
                exchange(array, j, j - 1);
            }
        }
    }

    /**
     * 使用插入排序算法对数组进行排序（将元素插入指定位置，其余元素向后移动）
     *
     * @param array
     * @param length
     */
    public static void insertionSort(int[] array, int length) {
        if (length <= 1) {
            return;
        }

        for (int i = 1; i < length; ++i) {
            int value = array[i];
            int j = i - 1;
            /*
             * 在已排序数组部分中寻找新元素要插入的位置（从第2个元素开始比较），对于元素>value的向后移动一个位置（在已排序区从后向前比较）
             */
            for (; j >= 0; --j) {
                if (array[j] > value) {
                    array[j + 1] = array[j];
                } else {
                    break;
                }
            }
            // array[j+1]为实际插入元素的位置，array[j]是比value小的元素
            array[j + 1] = value;
        }
    }

    /*public static void main(String[] args) {
        int[] array = new int[]{4, 5, 6, 3, 2, 1};
        // 0    [main] INFO  org.interview.algorithm.impl.InsertionSort  - all array data after sorting is: [1, 2, 3, 4, 5, 6]
        InsertionSort.insertionSort(array, array.length);
        logger.info("all array data after sorting is: {}", Arrays.toString(array));
    }*/

}
