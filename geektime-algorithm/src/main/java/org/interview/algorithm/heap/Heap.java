package org.interview.algorithm.heap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Sam Ma
 * @date 2020/10/08
 * 使用堆(Heap)实现堆排序，堆是一个完全二叉树、堆中个每一个结点值都大于等于(或小于等于)其子树中的每个结点
 */
public class Heap {

    private static final Logger LOGGER = LoggerFactory.getLogger(Heap.class);

    /**
     * 从下标1开始存储数据
     */
    private int[] data;

    /**
     * 堆中可存储的最大数据的个数
     */
    private int capacity;

    /**
     * 堆中已经存储数据的个数
     */
    private int count;

    public Heap(int capacity) {
        data = new int[capacity + 1];
        this.capacity = capacity;
        this.count = 0;
    }

    /**
     * 向堆中插入数据element
     *
     * @param element
     */
    public void insertValue(int element) {
        // 堆中已经没有空间，堆满了
        if (count >= capacity) return;
        ++count;
        data[count] = element;
        // 自下而上进行堆化，大顶堆的实现方式 (大元素放在堆顶)
        int i = count;
        while (i / 2 > 0 && data[i] > data[i / 2]) {
            // swap()函数的作用是交换下标为i和i/2的两个元素
            swap(data, i, i/2);
            i = i /2;
        }
    }

    /**
     * 交换堆中两个元素的位置
     *
     * @param data
     * @param indexa
     * @param indexb
     */
    private void swap(int[] data, int indexa, int indexb) {
        int temp = data[indexa];
        data[indexa] = data[indexb];
        data[indexb] = temp;
    }

    /**
     * 移除堆中最大的元素
     */
    public int removeMax() {
        if (count == 0) return -1;
        int maxElement = data[1];
        // 将堆中最后一个元素作为堆顶元素，然后进行自顶向下的堆化
        data[1] = data[count];
        --count;
        heapify(data, count, 1);
        return maxElement;
    }

    /**
     * 自顶向下进行堆化 (从新将最大的元素放到堆顶)
     *
     * @param data
     * @param count
     * @param index
     */
    private void heapify(int[] data, int count, int index) {
        while (true) {
            int maxPos = index;
            // 若当前结点的值小于其左子树结点(index * 2)，对其数值进行交换
            if (index * 2 <= count && data[index] < data[index * 2]) maxPos = index * 2;
            // 当前结点的数值小于其右子结点(index * 2 + 1), 将其与右子结点进行交换
            if (index * 2 + 1 <= count && data[maxPos] < data[index * 2 + 1]) maxPos = index * 2 + 1;
            if (maxPos == index) break;
            /*
             * 交换maxPos与当前index的位置，并且将交换后的较小元素继续下沉
             */
            swap(data, index, maxPos);
            index = maxPos;
        }
    }

    /**
     * 将数组数据进行堆化处理 (依据元素数组、数组数量count构堆结构)
     *
     * @param data
     * @param count
     */
    private void buildHeap(int[] data, int count) {
        for (int i = count / 2; i >= 1; --i) {
            heapify(data, count, i);
        }
    }

    /**
     * 对数组数据进行堆化处理 (其核心思想：将堆顶数据与数组元素交换，然后重新堆化. 所有元素堆化完成后，真个数组就是有序的)
     *
     * @param data
     * @param count
     */
    public int[] sortElements(int[] data, int count) {
        buildHeap(data, count);
        int k = count;
        while (k > 1) {
            swap(data, 1, k);
            --k;
            heapify(data, k, 1);
        }
        return data;
    }

    public static void main(String[] args) {
        Heap heap = new Heap(8);
        heap.insertValue(5);
        heap.insertValue(3);
        heap.insertValue(9);
        heap.insertValue(6);
        heap.insertValue(4);
        heap.insertValue(7);
        heap.insertValue(10);
        heap.insertValue(8);
        // 代码是基于大顶堆进行实现的，data数组的第一个元素为堆中的最大元素
        int maxElement = heap.removeMax();
        LOGGER.info("max element in heap is: [{}], all elements in data are: {}", maxElement, Arrays.toString(heap.data));

        Heap heapSort = new Heap(5);
        int[] elements = new int[]{0, 5, 3, 9, 6, 7};
        int[] results = heapSort.sortElements(elements, 5);
        // 0    [main] INFO  org.interview.algorithm.heap.Heap  - all elements after sorting is: [0, 3, 5, 6, 7, 9]
        LOGGER.info("all elements after sorting is: {}", Arrays.toString(results));
    }

}
