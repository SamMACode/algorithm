package org.interview.queue;

import java.util.Iterator;

/**
 * @author dong
 * @date 2019-02-07
 * className: MinPQ
 * Comparable weight edge
 */

public class MinPQ<Key extends Comparable<Key>> implements Iterable<Key> {

    private Key[] priorityQueue;

    private Integer elementNum;

    private static final Integer INIT_CAPACITY = 8;

    public MinPQ() {
        this.priorityQueue = (Key[])new Object[INIT_CAPACITY];
        elementNum = 0;
    }

    @Override
    public Iterator<Key> iterator() {
        return null;
    }


}
