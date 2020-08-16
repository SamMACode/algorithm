package org.interview.algorithm.impl;

import org.interview.algorithm.AbstractSort;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * SelectionSort选择排序算法的实现
 */
public class SelectionSort extends AbstractSort {

    @Override
    public void sort(Comparable[] array) {
        // 将数组array[]按照升序进行排序.
        int length = array.length;
        for (int i = 0; i < length; i++) {
            // 将array[i]与a[i+1..length]中最小的元素交换.
            int min = i;
            for (int j = i + 1; j < length; j++) {
                if (less(array[j], array[min])) {
                    min = j;
                }
                // 将元素进行交换将最小的元素与内循环中在位置j处的元素进行交换.
                exchange(array, i, min);
            }
        }
    }

}
