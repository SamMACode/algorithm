package org.interview.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * className: AbstractSort
 * Function list:
 *   1.sort: sort array item
 *   2.less: compare two value
 *   3.exchange: change array item value
 *   4.showItem: print all item in array
 * @author dong
 * @create 2018-07-09 下午10:45
 **/
@Slf4j
public abstract class AbstractSort {

    /**
     * Sort item which implement Comparable interface
     * @param array
     */
    public abstract void sort(Comparable[] array);

    /**
     * compare two value
     * @param prev
     * @param next
     * @return
     */
    public static boolean less(Comparable prev, Comparable next) {
        return prev.compareTo(next) < 0;
    }

    /**
     * change array item
     * @param array
     * @param from
     * @param to
     */
    public static void exchange(Comparable[] array, int from, int to) {
        Comparable item = array[from];
        array[from] = array[to];
        array[to] = item;
    }

    /**
     * print all item in array
     * @param array
     */
    public static void showItem(Comparable[] array) {
        log.info("Arrays elements: " + Arrays.toString(array));
    }

    /**
     * whether array is a sorted array
     * @param array
     * @return
     */
    public static boolean isSorted(Comparable[] array) {
        for(int index = 1; index < array.length; index++) {
            if(less(array[index], array[index-1])) {
                return false;
            }
        }
        return true;
    }
}
