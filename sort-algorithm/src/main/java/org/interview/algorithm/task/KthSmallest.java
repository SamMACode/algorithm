package org.interview.algorithm.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sam Ma
 * @date 2020/09/12
 * 找数组中第kth小的元素内容(使用快速排序的partition分区方法)
 */
public class KthSmallest {

    private static final Logger logger = LoggerFactory.getLogger(KthSmallest.class);

    /**
     * 找数组array中第kth小的元素
     *
     * @param array
     * @param kth
     * @return
     */
    public static int kthSmallest(int[] array, int kth) {
        if (array == null || array.length < kth) {
            return -1;
        }
        // 从array中获取partitionId，对分区后的元素进行排序
        int partition = partition(array, 0, array.length - 1);
        while (partition + 1 != kth) {
            if (partition + 1 < kth) {
                partition = partition(array, partition + 1, array.length - 1);
            } else {
                partition = partition(array, 0, partition - 1);
            }
        }
        return array[partition];
    }

    /**
     * 对数组数据array进行分区，其中取[start, end]中元素，并对partitionId元素进行排序
     *
     * @param array
     * @param start
     * @param end
     * @return
     */
    public static int partition(int[] array, int start, int end) {
        int pivot = array[end];

        int i = start;
        for (int j = start; j < end; j++) {
            // 这里要是<= 不然会出现死循环，比如查找数组[1, 1, 2]中第2小的元素
            if (array[j] <= pivot) {
                swap(array, i, j);
                i++;
            }
        }

        swap(array, i, end);
        return i;
    }

    /**
     * 对数组array[]中元素i和j进行交换
     *
     * @param array
     * @param itemA
     * @param itemB
     */
    public static void swap(int[] array, int itemA, int itemB) {
        if (itemA == itemB) {
            return;
        }
        int temp = array[itemA];
        array[itemA] = array[itemB];
        array[itemB] = temp;
    }

    /*public static void main(String[] args) {
        int[] array = new int[]{4, 5, 6, 3, 3, 2, 1};
        int value = KthSmallest.kthSmallest(array, 3);
        logger.info("3rd smallest item in array is: {}", value);
    }*/

}
