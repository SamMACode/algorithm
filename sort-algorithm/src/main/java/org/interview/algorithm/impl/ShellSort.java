package org.interview.algorithm.impl;

import org.interview.algorithm.AbstractSort;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * ShellSort希尔排序算法的实现
 */
public class ShellSort extends AbstractSort {

    /**
     * sort array using shell algorithm
     *
     * @param array
     */
    @Override
    public void sort(Comparable[] array) {
        // 将数组array按照升序进行排序.
        int length = array.length;
        int seed = 1;
        // 计算希尔shell的间隔大小 1, 4, 13, 40, 121
        while (seed < length / 3) {
            seed = 3 * seed + 1;
        }

        // 是数组成为{h区间长度内}有序[such as 4].
        while (seed >= 1) {
            for (int i = seed; i < length; i++) {
                // 将array[i]插入到array[i-h]、array[i-2*h]、array[i-3*h]之中.
                for (int j = i; j >= seed && less(array[j], array[j - seed]); j -= seed) {
                    exchange(array, j, j - seed);
                }
            }
            seed = seed / 3;
        }
    }

}
