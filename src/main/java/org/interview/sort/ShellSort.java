package org.interview.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * ShellSort: 希尔排序算法
 *
 * @author dong
 * @create 2018-07-10 下午10:05
 **/
@Slf4j
public class ShellSort extends AbstractSort {

    /**
     * sort array using shell algorithm
     * @param array
     */
    @Override
    public void sort(Comparable[] array) {
        // 将数组array按照升序进行排序.
        int length = array.length;
        int seed = 1;
        // 计算希尔shell的间隔大小 1, 4, 13, 40, 121
        while(seed < length/3) { seed = 3*seed + 1; }

        // 是数组成为{h区间长度内}有序[such as 4].
        while(seed >= 1) {
            for(int i = seed; i < length; i++) {
                // 将array[i]插入到array[i-h]、array[i-2*h]、array[i-3*h]之中.
                for (int j = i; j >= seed && less(array[j], array[j-seed]); j -= seed) {
                    exchange(array, j, j - seed);
                }
            }
            seed = seed/3;
        }
    }

    public static void main(String[] args) {
        Integer[] array = {1, 5, 3, 9, 7, 6, 3, 5, 1, 5};
        ShellSort shellSort = new ShellSort();
        shellSort.sort(array);
        // 向console中打印出排序之后的结果.
        log.info("sorted array: {}", Arrays.toString(array));
    }
}
