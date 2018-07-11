package org.interview.sort;

/**
 * 快速排序算法QuickSort
 *
 * @author dong
 * @create 2018-07-11 下午10:26
 **/
public class QuickSort extends AbstractBasicSortAlgorithm {

    @Override
    public void sort(Comparable[] array) {
        sort(array, 0, array.length-1);
    }

    private void sort(Comparable[] array, int lo, int hi) {
        if (hi <= lo)   return;
        int j = partition(array, lo, hi);   // 对数组进行切分.
        sort(array, lo, j-1);    // 将左半部分array[lo...j-1]排序
        sort(array, j+1, hi);    // 将有半部分array[j+1...hi]排序
    }

    private int partition(Comparable[] array, int lo, int hi) {
        // 将数组切分为array[lo...i-1], a[i], array[i+1...hi]
        int i = lo, j = hi + 1;     // 左右扫描指针.
        Comparable v = array[lo];   // 切分元素.
        while(true) {
            // 扫描左右，检查扫描是否结束并交换元素.
            while(less(array[++i], v))  if(i == hi) break;
            while(less(v, array[--j]))  if(j == lo) break;
            if(i >= j) break;
            exch(array, lo, j);
        }
        exch(array, lo, j);     // 将v = a[j]放在正确的位置上.
        return j;               // array[lo...j-1] <= array[j] <= array[j+1..hi].
    }

}
