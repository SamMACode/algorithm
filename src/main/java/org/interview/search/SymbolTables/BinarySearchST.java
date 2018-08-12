package org.interview.search.SymbolTables;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 最基础的使用两个数组Array存储Key-value值
 *
 * @author dong
 * @create 2018-08-12 下午1:23
 **/
@Slf4j
public class BinarySearchST<Key extends Comparable<Key>, Value> {

    // 定义二维数组的初始容量大小.
    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] values;
    // 使用N表示当前Key[]和Value[]数组中已经存放的元素的个数.
    private int N = 0;

    public BinarySearchST() {
        this(INIT_CAPACITY);
    }

    /**
     * @description: 使用给定的capacity初始化二维数组的内容.
     * @param capacity: 设置数组的初始大小.
     * */
    public BinarySearchST(int capacity) {
        keys = (Key[])new Comparable[capacity];
        values = (Value[])new Comparable[capacity];
    }

    /**
     * @description: 当存放数组元素的key-value值已经接近饱和的时候,
     *  需要对数组大小进行调整。此时采用的一种方式就是简单的对象拷贝。
     *  warning: 当新分配的元素个数特别大的时候,分配连续的内存空间可能会失败.
     * */
    private void resize(int newSize) {
        assert newSize >= N;
        Key[] temKeys = (Key[]) new Comparable[newSize];
        Value[] temValues = (Value[]) new Comparable[newSize];
        // 将原来数组中的内容拷贝到现有数组中.
        for(int i = 0; i < N; i++) {
            temKeys[i] = keys[i];
            temValues[i] = values[i];
        }
        keys = temKeys;
        values = temValues;
    }

    public boolean isContains(Key key) {
        return get(key) != null;
    }

    /**
     * 返回符号表中对应元素的个数.
     * */
    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * @desc: 查找给定key对应的Value内容.
     * */
    public Value get(Key key) {
        if(isEmpty())   return null;
        int index = rank(key);
        if(index < N && keys[index].compareTo(key) == 0)  return values[index];
        return null;
    }

    /**
     * 使用二分搜索查找给定的key的位置,返回的是该Key对应的index的位置.
     * */
    public int rank(Key key) {
        int lo = 0, hi = N - 1;
        while(lo <= hi) {
            int middle = lo + (hi-lo)/2;
            int cmp = key.compareTo(keys[middle]);
            if (cmp < 0)        hi = middle - 1;
            else if (cmp > 0)   lo = middle + 1;
            else                return middle;
        }
        return lo;
    }

    /**
     * @desc:向符号表中插入给定的元素.
     * @param key 要进行插入的key的值.
     * @param value 要进行插入的value的值.
     * */
    public void put(Key key, Value value) {
        if(value == null) {
            deleteKey(key);
            return;
        }
        int index = rank(key);      // 返回给定key对应的index下标索引.
        if(index < N && keys[index].compareTo(key) == 0) {
            values[index] = value;
            return;
        }
        // 在插入新的元素之前先检查是否有足够的空间存储新的元素,当元素内容已经饱和则重新调整元素的个数2*keys.length.
        if (size() == N) resize(2*keys.length);
        // 将要插入元素位置之后的所有元素全部向后移动一个元素位置.
        for(int j = N; j > index; j--) {
            keys[j] = keys[j-1];
            values[j] = values[j-1];
        }
        keys[index] = key;
        values[index] = value;
        N++;    // 新增一个元素后将整个元素个数都整体+1.
    }

    /**
     * @desc:删除给定的键值对Key-value(如果存在于当前符号表中的话).
     * */
    public void deleteKey(Key key) {
        if(isEmpty())   return;
        int index = rank(key);
        if(index == N || keys[index].compareTo(key) != 0)    return;
        for(int j = index; j < N; j++) {
            keys[j] = keys[j+1];
            values[j] = values[j+1];
        }
        N--;        // 删除了一个元素将元素的个数减少1.
        keys[N] = null;
        values[N] = null;
        // 当符号表中元素的个数小于N/4的时候,对其大小进行重新的resize.
        if(N > 0 && N <= keys.length/4)  resize(keys.length/2);
    }

    /**
     * Ordered symbol table methods
     * */
    public Key min() {
        if(isEmpty())   return null;
        return keys[0];
    }
    public Key max() {
        if(isEmpty())   return null;
        return keys[N-1];
    }
    public Key select(int k) {
        if(k < 0 || k >= N) return null;
        return keys[k];
    }

    /**
     * @desc:返回给定范围内的所有key的值信息.
     * */
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new ArrayDeque<Key>();
        if(lo == null && hi == null)    return queue;
        if(lo.compareTo(hi) > 0)        return queue;
        for(int i = rank(lo); i < rank(hi); i++)
            queue.add(keys[i]);
        if(isContains(hi))   queue.add(hi);
        return queue;
    }

    public static void main(String[] args) {
        BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
        st.put("hello", 3);
        st.put("hello", 4);
        log.info("hello对应的值为: {}", st.get("hello"));
    }

}
