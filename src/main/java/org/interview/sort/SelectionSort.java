package org.interview.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 选择排序的算法
 *
 * @author dong
 * @create 2018-07-09 下午11:03
 **/
@Slf4j
public class SelectionSort extends AbstractSort {

    @Override
    public void sort(Comparable[] array) {
        // 将数组array[]按照升序进行排序.
        int length = array.length;
        for(int i = 0; i < length; i++) {
            // 将array[i]与a[i+1..length]中最小的元素交换.
            int min = i;
            for(int j = i+1; j < length; j++) {
                if(less(array[j], array[min])) { min = j; }
                // 将元素进行交换将最小的元素与内循环中在位置j处的元素进行交换.
                exchange(array, i, min);
            }
        }
    }

    public static void main(String[] args) {
        Integer[] array = {1, 5, 3, 9, 7};
        SelectionSort selection = new SelectionSort();
        selection.sort(array);
        // 向console中打印出排序之后的结果.
        System.out.println("sorted array: { " + Arrays.toString(array) + " }");
        // log.info("sorted array: {}", array);
    }
}
