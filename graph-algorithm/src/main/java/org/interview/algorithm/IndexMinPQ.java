package org.interview.algorithm;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * 定义IndexMinPQ接口内容 实现按index排序元素
 */
public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Key> {

    /**
     * number of elements
     */
    private int elementNum;

    private int[] priorityQueue;

    private int[] reverseIndex;

    private Key[] keyList;

    public IndexMinPQ(Integer number) {
        keyList = (Key[]) new Comparable[number + 1];
        priorityQueue = new int[number + 1];
        reverseIndex = new int[number + 1];
        Arrays.stream(reverseIndex).forEach(element -> element = -1);
    }

    /**
     * whether this queue is empty
     *
     * @return
     */
    public boolean isEmpty() {
        return elementNum == 0;
    }

    /**
     * whether contains this key
     *
     * @param key
     * @return
     */
    public boolean contains(Integer key) {
        return this.reverseIndex[key] == -1;
    }

    /**
     * return queue element size
     *
     * @return
     */
    public int size() {
        return this.elementNum;
    }

    /**
     * insert element to target property
     *
     * @param index
     * @param key
     */
    public void insert(int index, Key key) {
        if (contains(index)) {
            throw new RuntimeException("item already exist in queue");
        }
        elementNum++;
        priorityQueue[index] = elementNum;
        reverseIndex[elementNum] = index;
        keyList[index] = key;
        // todo swim(key) for [sort queue element by key]
    }

    /**
     * insert key to WeightGraph
     *
     * @param key
     */
    public void insert(Key key) {
        // todo insert key
    }

    /**
     * return the minimal key
     *
     * @return
     */
    public Key minKey() {
        if (elementNum == 0) {
            throw new RuntimeException("Priority queue underflow");
        }
        return keyList[priorityQueue[1]];
    }

    /**
     * delete a minimal key and return this associate key
     *
     * @return
     */
    public Key delMinKey() {
        if (elementNum == 0) {
            throw new RuntimeException("Priority queue underflow");
        }
        int minElemIndex = priorityQueue[1];
        // todo exchange()
        // todo sink()
        reverseIndex[minElemIndex] = -1;
        keyList[priorityQueue[elementNum + 1]] = null;
        priorityQueue[elementNum + 1] = -1;

        return keyList[0];
    }

    /**
     * delete a minimal key and return this associate index
     *
     * @return
     */
    public Integer delMin() {
        return -1;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    public void change(Integer index, Key value) {
        // todo
    }

}
