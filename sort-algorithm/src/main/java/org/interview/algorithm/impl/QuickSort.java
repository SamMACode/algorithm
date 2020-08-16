package org.interview.algorithm.impl;

import org.interview.algorithm.AbstractSort;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * QuickSort快速排序算法的实现
 */
public class QuickSort extends AbstractSort {

    @Override
    public void sort(Comparable[] array) {
        // 将数组的元素打乱shuffle.
        Collections.shuffle(Arrays.asList(array));
        sortRange(array, 0, array.length - 1);
    }

    /**
     * sort array item, range [lo...hi]
     *
     * @param array
     * @param lo
     * @param hi
     */
    private void sortRange(Comparable[] array, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        // 对数组进行切分.
        int j = partition(array, lo, hi);
        // 将左半部分array[lo...j-1]排序
        sortRange(array, lo, j - 1);
        // 将有半部分array[j+1...hi]排序
        sortRange(array, j + 1, hi);
    }

    /**
     * array[lo...j-1] <= array[j] <= array[j+1..hi].
     *
     * @param array
     * @param lo
     * @param hi
     * @return
     */
    private int partition(Comparable[] array, int lo, int hi) {
        // 将数组切分为array[lo...i-1], a[i], array[i+1...hi]
        int i = lo, j = hi + 1;
        Comparable v = array[lo];
        while (true) {
            // 扫描左右，检查扫描是否结束并交换元素.
            while (less(array[++i], v)) {
                if (i == hi) {
                    break;
                }
            }
            while (less(v, array[--j])) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            exchange(array, lo, j);
        }
        // 将v = a[j]放在正确的位置上.
        exchange(array, lo, j);
        return j;
    }

}
