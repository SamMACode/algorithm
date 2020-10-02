package org.interview.algorithm.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sam Ma
 * @date 2020/09/24
 * 实现堆排序算法，其是原地排序算法(不需要辅助数组)，常用于求kth大元素以及中位数(分为大、小堆处理)
 */
public class HeapSort {

    private static final Logger logger = LoggerFactory.getLogger(HeapSort.class);

    // data数组，从下标1的位置开始存储数据
    private int[] data;

    // 在heap堆中可以存储的最大元素数量
    private int capacity;

    // head堆中已经存储元素的个数(count <= capacity)
    private int count;

    public HeapSort(int capacity) {
        data = new int[capacity];
        this.capacity = capacity;
        count = 0;
    }

    /**
     * 将数据value插入到data堆队列中
     *
     * @param value
     */
    public void insertValue(int value) {
        if (count > capacity) return;
        ++count;
        data[count] = value;
        // 在while循环中自底向上进行堆化，swapValue()函数的作用是交换下标为i和i/2的两个元素
        int i = count;
        while (i / 2 > 0 && data[i] > data[i / 2]) {
            swapValue(data, i, i / 2);
            i = i / 2;
        }
    }

    /**
     * 从堆中删除最大的元素，然后重新实现heapify化
     */
    public void removeMaxElement() {
        if (count == 0) return;
        data[1] = data[count];
        --count;
        heapify(data, count, 1);
    }

    /**
     * 对heap中data数据执行自上向下的堆化
     *
     * @param array
     * @param count
     * @param index
     */
    private void heapify(int[] array, int count, int index) {
        while (true) {
            int maxPos = index;
            if (index * 2 <= count && data[index] < data[index * 2]) maxPos = index * 2;
            if (index * 2 <= count && data[maxPos] < data[index * 2 + 1]) {
                maxPos = index * 2 + 1;
            }
            if (maxPos == index) break;
            swapValue(array, index, maxPos);
            index = maxPos;
        }
    }

    /**
     * 根据下标索引对数组data中的元素进行交换
     *
     * @param data
     * @param indexA
     * @param indexB
     */
    private void swapValue(int[] data, int indexA, int indexB) {
        int tempValue = data[indexA];
        data[indexA] = data[indexB];
        data[indexB] = tempValue;
    }

    /**
     * 使用data中的数据构建堆排序array，并且对其内容进行排序
     */
    public void buildHeapAndSort() {
        for (int i = count / 2; i >= 1; --i) {
            heapify(data, count, i);
        }
        int k = count;
        while (k > 1) {
            swapValue(data, 1, k);
            --k;
            heapify(data, k, 1);
        }
    }

}
