package org.interview.algorithm.task;

import org.interview.algorithm.impl.QuickSort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Sam Ma
 * @date 2020/09/12
 * bucket sort桶排序算法的实现（根据数值范围以及bucketSize确定桶的数量，对桶中的元素使用快序）
 */
public class BucketSort {

    private static final Logger logger = LoggerFactory.getLogger(BucketSort.class);

    /**
     * 桶排序算法的实现，array为数组 bucketSize为桶的大小
     *
     * @param array
     * @param bucketSize
     */
    public static void bucketSort(int[] array, int bucketSize) {
        if (array.length < 2) {
            return;
        }
        // 1.取数组中的最大及最小的值, 根据桶的数量确定每个桶数值的取值范围
        int minValue = array[0];
        int maxValue = array[1];

        for (int i = 0; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            } else if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }

        // 根据最大值、最小值以及bucketSize的大小确定桶的数量, indexArray为每个桶中最新元素的下标索引
        int bucketCount = (maxValue - minValue) / bucketSize + 1;
        int[][] buckets = new int[bucketCount][bucketSize];
        int[] indexArray = new int[bucketCount];

        // 2.将数组中个各个值分配到bucket里，根据取之范围判断元素应该放在哪个桶，同时更新indexArray中位置
        for (int i = 0; i < array.length; i++) {
            int bucketIndex = (array[i] - minValue) / bucketSize;
            /*
             * 对数组元素进行扩容（当桶中元素已经放满时，indexArray[bucketIndex]保存当前放置元素数量）
             */
            if (indexArray[bucketIndex] == buckets[bucketIndex].length) {
                ensureCapacity(buckets, bucketIndex);
            }
            buckets[bucketIndex][indexArray[bucketIndex]++] = array[i];
        }

        /*
         * 3.对每个桶内的元素进行快速排序，最终按桶的顺序依次取出桶中的元素可得到完整的排序()
         */
        int iterator = 0;
        for (int i = 0; i < bucketCount; i++) {
            if (indexArray[i] == 0) {
                continue;
            }
            QuickSort.quickSort(buckets[i], indexArray[i]);
            // 排完顺序后，将最新的顺序重新写回到array数组中
            for (int j = 0; j < indexArray[i]; j++) {
                array[iterator++] = buckets[i][j];
            }
        }
    }

    /**
     * 对buckets数组进行扩容
     *
     * @param buckets
     * @param bucketIndex
     */
    private static void ensureCapacity(int[][] buckets, int bucketIndex) {
        int[] tempArray = buckets[bucketIndex];
        int[] expandBucket = new int[tempArray.length * 2];
        // 将之前桶中的所有元素拷贝到新扩容后的桶中，同时更新buckets[index]引用指向新的桶
        for (int i = 0; i < tempArray.length; i++) {
            expandBucket[i] = tempArray[i];
        }
        buckets[bucketIndex] = expandBucket;
    }

    /*public static void main(String[] args) {
        int[] array = new int[]{11, 8, 3, 9, 7, 1, 2, 5, 23, 0, 9, 5, 4, 11, 15};
        BucketSort.bucketSort(array, 3);
        logger.info("bucket sort results are: {}", Arrays.toString(array));
    }*/

}
