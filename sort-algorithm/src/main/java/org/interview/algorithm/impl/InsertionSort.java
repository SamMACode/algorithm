package org.interview.algorithm.impl;

import org.interview.algorithm.AbstractSort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * InsertionSort插入排序算法的实现
 */
public class InsertionSort extends AbstractSort {

    private static final Logger logger = LoggerFactory.getLogger(InsertionSort.class);

    @Override
    public void sort(Comparable[] array) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            // 将array[i]插入到a[i-1]/a[i-2]/a[i-3]..之中,确保的是i之前的数组序列是完全有序地.
            for (int j = i; j > 0 && less(array[j], array[j - 1]); j--) {
                // 对数组array中的元素进行排序,当存在array[j]<array[j-1]的情况,则对其进行交换.
                exchange(array, j, j - 1);
            }
        }
    }

}
