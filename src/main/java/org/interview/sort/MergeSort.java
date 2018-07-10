package org.interview.sort;

/**
 * 归并排序算法MergeSort
 *
 * @author dong
 * @create 2018-07-10 下午10:43
 **/
public class MergeSort extends AbstractBasicSortAlgorithm {

    private static Comparable[] aux;    // 归并所需要的辅助数组.

    @Override
    public void sort(Comparable[] array) {
        aux = new Comparable[array.length];     // 一次性的分配足够的空间.
        sort(array, 0, array.length - 1);
    }

    private void sort(Comparable[] array, int lo, int hi) {
        // 将数组array[lo..hi]排序.
        if(hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(array, lo, mid);           // 将左半部分进行排序.
        sort(array, mid+1, hi);     // 将右半部分进行排序.
        merge(array, lo, mid, hi);      // 归并结果
    }

    public static void merge(Comparable[] array, int lo, int mid, int hi) {
        // 将array[lo..mid]和array[mid+1...hi]进行归并.
        int i = lo, j = mid + 1;
        for(int k = lo; k <= hi; k++) {     // 将array[lo..hi]复制到aux[lo...hi]
            aux[k] = array[k];
        }
        for(int k = lo; k <= hi; k++) {     // 将array结果归并到array[lo..hi]
            if (i > mid)      array[k] = aux[j++];
            else if (j > hi)  array[k] = aux[i++];
            else if (less(aux[j], aux[i]))  array[k] = aux[j++];
            else              array[k] = aux[i++];
        }
    }
}
