package org.interview.algorithm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * MergeSort归并排序算法的实现
 */
public class MergeSort {

    private static final Logger logger = LoggerFactory.getLogger(MergeSort.class);

    /**
     * 归并排序算法，array为要排序的数组，length为数组的长度
     *
     * @param array
     * @param length
     */
    public static void mergeSort(int[] array, int length) {
        mergeSortInternally(array, 0, length - 1);
    }

    /**
     * 递归调用的函数(取区间的中位数，然后分别对区间元素进行排序)
     *
     * @param array
     * @param start
     * @param end
     */
    private static void mergeSortInternally(int[] array, int start, int end) {
        if (start >= end) return;

        // 取start和end之间的中间位置middle，防止(start+end)的和大于int类型最大值
        int middle = start + (end - start) / 2;
        // 分治递归(对[start, middle], [middle+1, end]取件的元素进行分别排序)
        mergeSortInternally(array, start, middle);
        mergeSortInternally(array, middle + 1, end);

        // 将array[start...middle]和array[middle+1...end]合并为array[start...end]
//        merge(array, start, middle, end);
        mergeBySentry(array, start, middle, end);
    }

    /**
     * 对归并排序后的结果进行合并
     *
     * @param array
     * @param start
     * @param middle
     * @param end
     */
    private static void merge(int[] array, int start, int middle, int end) {
        int i = start;
        int j = middle + 1;
        int k = 0;
        // 申请一个大小跟array[start...end]一样的临时数组
        int[] tempArray = new int[end - start + 1];
        while (i <= middle && j <= end) {
            if (array[i] <= array[j]) {
                tempArray[k++] = array[i++];
            } else {
                tempArray[k++] = array[j++];
            }
        }

        // 判断哪个子数组中还存在剩余的数据
        int tmpStart = i;
        int tmpEnd = middle;
        if (j <= end) {
            tmpStart = j;
            tmpEnd = end;
        }

        // 将剩余元素拷贝到临时数组中
        while (tmpStart <= tmpEnd) {
            tempArray[k++] = array[tmpStart++];
        }
        // 将temp数组的内容拷贝回array[start...end]
        for (i = 0; i <= end - start; ++i) {
            array[start + i] = tempArray[i];
        }

    }

    /**
     * 使用哨兵模式合并归并后的数组
     * @param array
     * @param start
     * @param middle
     * @param end
     */
    public static void mergeBySentry(int[] array, int start, int middle, int end) {
        int[] leftArray = new int[middle - start + 2];
        int[] rightArray = new int[end - start + 1];

        for (int i = 0; i <= middle - start; i++) {
            leftArray[i] = array[start + i];
        }
        // 第一个数组添加哨兵
        leftArray[middle - start + 1] = Integer.MAX_VALUE;
        for (int i = 0; i < end - middle; i++) {
            rightArray[i] = array[middle + 1 + i];
        }

        // 第二个数组添加哨兵
        rightArray[end - middle] = Integer.MAX_VALUE;

        int i = 0;
        int j = 0;
        int k = start;
        while (k <= end) {
            // 当左边数组到达哨兵值时，i不再增加，直到右边数组读取完剩余值，同理右边数组也一样
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }

    }

    /*public static void main(String[] args) {
        int[] array = new int[]{11, 8, 3, 9, 7, 1, 2, 5};
        MergeSort.mergeSort(array, array.length);
        // 0    [main] INFO  org.interview.algorithm.impl.MergeSort  - all array data after sorting is: [1, 2, 3, 5, 7, 8, 9, 11]
        logger.info("all array data after sorting is: {}", Arrays.toString(array));
    }*/

}
