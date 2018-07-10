package org.interview.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * shell希尔排序算法
 *
 * @author dong
 * @create 2018-07-10 下午10:05
 **/
@Slf4j
public class ShellSort extends AbstractBasicSortAlgorithm {

    @Override
    public void sort(Comparable[] array) {
        // 将数组array按照升序进行排序.
        int length = array.length;    // such as 10.
        int h = 1;
        while(h < length/3) h = 3*h + 1;     // 计算希尔shell的间隔大小 1, 4, 13, 40, 121
        // 是数组成为{h区间长度内}有序.
        while(h >= 1) {    // such as 4.
            for(int i = h; i < length; i++) {
                // 将array[i]插入到array[i-h]、array[i-2*h]、array[i-3*h]之中.
                for(int j = i; j >= h && less(array[j], array[j-h]); j -= h)
                    exch(array, j, j-h);
            }
            h = h/3;
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
