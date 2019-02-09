package org.interview.queue;

import java.util.Iterator;

/**
 * @author dong
 * @date 2019-02-07
 * className: MinPQ
 * Comparable weight edge
 */

public class MinPQ<Key extends Comparable<Key>> implements Iterable<Key> {

    /**
     * store item based index 1
     */
    private Key[] priorityQueue;

    private Integer elementNum;

    private static final Integer INIT_CAPACITY = 8;

    public MinPQ() {
        this.priorityQueue = (Key[])new Object[INIT_CAPACITY];
        elementNum = 0;
    }

    public MinPQ(int capacity) {
        priorityQueue = (Key[]) new Object[capacity+1];
        elementNum = 0;
    }

    /**
     * create a priority using given items.
     * Takes time proportional to the number of items using sink-based heap construction
     * @param keys
     */
    public MinPQ(Key[] keys) {
        elementNum = keys.length;
        priorityQueue = (Key[]) new Object[keys.length+1];

        for(int i = 0; i < keys.length; i++) {
            priorityQueue[i+1] = keys[i];
        }

        // sort priorityQueue item list
        for(int j = elementNum/2; j >= 1; j--) {
            sinkItem(j);
        }
    }

    /**
     * helper function
     * @param j
     */
    private void sinkItem(int j) {
        // todo sort item list

    }

    public Key delMinKey() {
        if (elementNum == 0) {
            throw new RuntimeException("Priority queue underflow");
        }
        exchange(1, elementNum);
        Key minKey = priorityQueue[elementNum--];
        sinkItem(1);
        priorityQueue[elementNum+1] = null;
        return minKey;
    }

    /**
     *
     * @param i
     * @param elementNum
     */
    private void exchange(int i, Integer elementNum) {
        // todo exchange item

    }


    @Override
    public Iterator<Key> iterator() {
        return null;
    }


    public boolean isEmpty() {
        return elementNum == 0;
    }

    /**
     * insert item to priority queue
     * @param key
     */
    public void insert(Key key) {
        priorityQueue[++elementNum] = key;
        swimItem(key);
    }

    private void swimItem(Key key) {
        // todo finish swimItem method
    }
}
