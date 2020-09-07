package org.interview.algorithm.impl;

import org.interview.algorithm.AbstractSort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * SelectionSort选择排序算法的实现（每次从未排序区取最小元素，然后放到已排序区后面）
 */
public class SelectionSort extends AbstractSort {

    private static final Logger logger = LoggerFactory.getLogger(SelectionSort.class);

    /**
     * 选择排序（select sort algorithm）算法的实现
     *
     * @param array
     */
    @Override
    public void sort(Comparable[] array) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            /*
             * 从未排序区中找出找出最小的元素的下标索引，将最小元素赋予到已排序区的最后
             */
            int minIndex = i;
            for (int j = i + 1; j < length; j++) {
                if (less(array[j], array[minIndex])) {
                    minIndex = j;
                }
            }
            exchange(array, i, minIndex);
        }
    }

    /*public static void main(String[] args) {
        Integer[] array = new Integer[]{4, 5, 6, 3, 2, 1};
        // 0    [main] INFO  org.interview.algorithm.impl.SelectionSort  - all array data after sorting is: [1, 2, 3, 4, 5, 6]
        new SelectionSort().sort(array);
        logger.info("all array data after sorting is: {}", Arrays.toString(array));
    }*/

}
