package org.interview.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 初级排序算法
 *
 * @author dong
 * @create 2018-07-09 下午10:45
 **/
@Slf4j
public abstract class AbstractBasicSortAlgorithm {

    /**
     * 实现排序的逻辑.Comparable[] a
     */
    public abstract void sort(Comparable[] a);

    /**
     * @param v
     * */
    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    /**
     * @param a
     * 对数组中内容进行交换.
     * */
    public static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /**
     * description: 向控制台打印出Comparable数组的内容.
     * */
    public static void show(Comparable[] a) {
        log.info("Arrays elements: " + Arrays.toString(a));
    }

    /**
     * description: 判断当前数组是否处于有序地状态.
     * */
    public static boolean isSorted(Comparable[] array) {
        for(int i = 1; i < array.length; i++) {
            if(less(array[i], array[i-1])) return false;
        }
        return true;
    }
}
