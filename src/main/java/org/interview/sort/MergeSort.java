package org.interview.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * MergeSort:归并排序算法MergeSort
 *
 * @author dong
 * @create 2018-07-10 下午10:43
 **/
@Slf4j
public class MergeSort extends AbstractSort {

    /**
     * 归并所需要的辅助数组
     */
    private static Comparable[] aux;

    /**
     * sort array item
     * @param array
     */
    @Override
    public void sort(Comparable[] array) {
        aux = new Comparable[array.length];
        sort(array, 0, array.length - 1);
    }

    /**
     * sort array from lo..hi in array
     * @param array
     * @param lo
     * @param hi
     */
    private void sort(Comparable[] array, int lo, int hi) {
        if(hi <= lo) {return;}
        int mid = lo + (hi - lo) / 2;

        // 将mid位置左半部分进行排序.
        sort(array, lo, mid);
        // 将mid位置右半部分进行排序.
        sort(array, mid+1, hi);
        // merge sorted array
        merge(array, lo, mid, hi);
    }

    /**
     * merge two sorted array
     * @param array
     * @param lo
     * @param mid
     * @param hi
     */
    public static void merge(Comparable[] array, int lo, int mid, int hi) {
        // 将array[lo..mid]和array[mid+1...hi]进行归并.
        int i = lo, j = mid + 1;
        // 将array[lo..hi]复制到aux[lo...hi]
        for(int k = lo; k <= hi; k++) {
            aux[k] = array[k];
        }

        // 将array结果归并到array[lo..hi]
        for(int k = lo; k <= hi; k++) {
            if (i > mid)      { array[k] = aux[j++]; }
            else if (j > hi)  { array[k] = aux[i++]; }
            else if (less(aux[j], aux[i]))  {array[k] = aux[j++]; }
            else              { array[k] = aux[i++]; }
        }
    }

    public static void main(String[] args) {
        Integer[] array = {1, 5, 3, 9, 7};
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(array);
        // 向console中打印出排序之后的结果.
        log.info("sorted array: {}", Arrays.toString(array));
    }

}
