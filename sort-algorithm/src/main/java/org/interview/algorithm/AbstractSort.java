package org.interview.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * 定义集合排序的抽象类AbstractSort
 */
public abstract class AbstractSort {

    private static final Logger logger = LoggerFactory.getLogger(AbstractSort.class);

    /**
     * Sort item which implement Comparable interface
     *
     * @param array
     */
    public abstract void sort(Comparable[] array);

    /**
     * compare two value
     *
     * @param prev
     * @param next
     * @return
     */
    public static boolean less(Comparable prev, Comparable next) {
        return prev.compareTo(next) < 0;
    }

    /**
     * change array item
     *
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
     *
     * @param array
     */
    public static void showItem(Comparable[] array) {
        logger.info("Arrays elements: " + Arrays.toString(array));
    }

    /**
     * whether array is a sorted array
     *
     * @param array
     * @return
     */
    public static boolean isSorted(Comparable[] array) {
        for (int index = 1; index < array.length; index++) {
            if (less(array[index], array[index - 1])) {
                return false;
            }
        }
        return true;
    }

}
