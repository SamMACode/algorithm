package org.interview.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 插入排序算法
 *
 * @author dong
 * @create 2018-07-10 下午9:45
 **/
@Slf4j
public class InsertionSort extends AbstractBasicSortAlgorithm {

    @Override
    public void sort(Comparable[] array) {
        int length = array.length;
        for(int i = 0; i < length; i++) {
            // 将array[i]插入到a[i-1]/a[i-2]/a[i-3]..之中,确保的是i之前的数组序列是完全有序地.
            for(int j = i; j > 0 && less(array[j], array[j-1]); j--) {
                // 对数组array中的元素进行排序,当存在array[j]<array[j-1]的情况,则对其进行交换.
                exch(array, j, j-1);
            }
        }
    }

    public static void main(String[] args) {
        Integer[] array = {1, 5, 3, 9, 7};
        InsertionSort selection = new InsertionSort();
        selection.sort(array);
        // 向console中打印出排序之后的结果.
        log.info("sorted array: {}", Arrays.toString(array));
    }

}
