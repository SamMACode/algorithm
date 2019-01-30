package org.interview.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;

/**
 * 快速排序算法QuickSort
 *
 * FunctionList:
 *  1.sort: sort array items
 *  2.sortRange: sort range array items
 *  3.partition: partition array item
 * @author dong
 * @create 2018-07-11 下午10:26
 **/
@Slf4j
public class QuickSort extends AbstractSort {

    @Override
    public void sort(Comparable[] array) {
        // 将数组的元素打乱shuffle.
        Collections.shuffle(Arrays.asList(array));
        sortRange(array, 0, array.length-1);
    }

    /**
     * sort array item, range [lo...hi]
     * @param array
     * @param lo
     * @param hi
     */
    private void sortRange(Comparable[] array, int lo, int hi) {
        if (hi <= lo) { return; }
        // 对数组进行切分.
        int j = partition(array, lo, hi);
        // 将左半部分array[lo...j-1]排序
        sortRange(array, lo, j-1);
        // 将有半部分array[j+1...hi]排序
        sortRange(array, j+1, hi);
    }

    /**
     * array[lo...j-1] <= array[j] <= array[j+1..hi].
     * @param array
     * @param lo
     * @param hi
     * @return
     */
    private int partition(Comparable[] array, int lo, int hi) {
        // 将数组切分为array[lo...i-1], a[i], array[i+1...hi]
        int i = lo, j = hi + 1;
        Comparable v = array[lo];
        while(true) {
            // 扫描左右，检查扫描是否结束并交换元素.
            while(less(array[++i], v)) {
                if(i == hi) { break; }
            }
            while(less(v, array[--j])) {
                if(j == lo) { break; }
            }
            if(i >= j) { break; }
            exchange(array, lo, j);
        }
        // 将v = a[j]放在正确的位置上.
        exchange(array, lo, j);
        return j;
    }

    public static void main(String[] args) {
        Integer[] array = {1, 5, 3, 9, 7};
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(array);
        // 向console中打印出排序之后的结果.
        log.info("sorted array: {}", Arrays.toString(array));
    }

}
