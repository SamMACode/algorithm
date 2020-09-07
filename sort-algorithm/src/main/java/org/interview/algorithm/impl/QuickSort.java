package org.interview.algorithm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * QuickSort快速排序算法的实现
 */
public class QuickSort {

    private static final Logger logger = LoggerFactory.getLogger(QuickSort.class);

    /**
     * 使用快速排序算法对数组进行排序
     *
     * @param array  要排序的数组
     * @param length 数组的长度
     */
    public static void quickSort(int[] array, int length) {
        quickSortInternally(array, 0, length - 1);
    }

    /**
     * 使用快速排序算法内部进行排序
     *
     * @param array
     * @param start
     * @param end
     */
    private static void quickSortInternally(int[] array, int start, int end) {
        if (start >= end) return;
        /*
         * 快速排序思想：若要排序数组中下标从p到r之间的一组数据，选择p到r之间的任意一个数据作为pivot分区点。将[p..r]中小于pivot的元素
         *  放入其左边，大于pivot的元素放到右边
         */
        int partition = partition(array, start, end);
        quickSortInternally(array, start, partition - 1);
        /*
         * 根据分治、递归处理思想，可以使用递归排序下标从p到q-1之间的数据和下标从q+1~r之间的数据，直到区间压缩为1 就说明所有元素都是有序的
         */
        quickSortInternally(array, partition + 1, end);
    }

    /**
     * 用于获取partitionId
     *
     * @param array 要进行排序的数组元素
     * @param start
     * @param end
     * @return
     */
    private static int partition(int[] array, int start, int end) {
        // geektime默认是每次将数组的最后一个元素作为分区点
        int pivot = array[end];

        int i = start;
        for (int j = start; j < end; ++j) {
            if (array[j] < pivot) {
                if (i == j) {
                    ++i;
                } else {
                    int tmp = array[i];
                    array[i++] = array[j];
                    array[j] = tmp;
                }
            }
        }

        int tmp = array[i];
        array[i] = array[end];
        array[end] = tmp;
        logger.info("partition id is: {}", i);
        return i;
    }

    /*public static void main(String[] args) {
        int[] array = new int[]{11, 8, 3, 9, 7, 1, 2, 5};
        QuickSort.quickSort(array, array.length);
        // [main] INFO  org.interview.algorithm.impl.QuickSort  - all array data after sorting is: [1, 2, 3, 5, 7, 8, 9, 11]
        logger.info("all array data after sorting is: {}", Arrays.toString(array));
    }*/

}
