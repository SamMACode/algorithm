package org.interview.search.symboltables;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 使用链表的方式实现符号表
 *
 * @author dong
 * @create 2018-08-12 下午3:17
 **/
public class SequentialSearchTable<Key, Value> {
    /**
     * 用于统计符号表中的元素的个数.
     */
    private int number;

    /**
     * 定义链表中的头结点.
     */
    private Node first;

    private class Node {
        private Key key;
        private Value value;
        private Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public int size() {
        return number;
    }
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * does this symbol table contain the given key?
     * @param key
     * @return
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     * 根据给定的Key值返回其对应的Value值
     * @param key
     * @return
     */
    public Value get(Key key) {
        for(Node x = first; x != null; x = x.next) {
            if(key.equals(x.key)) { return x.value; }
        }
        return null;
    }

    /**
     * 将给定的Key.value键值对添加到符号表中,如果key的值为Null则直接删除元素.
     * @param key
     * @param value
     */
    public void putItem(Key key, Value value) {
        if(value == null) {
            deleteKey(key);
            return;
        }
        for(Node x = first; x != null; x = x.next) {
            if(key.equals(x.key)) {
                x.value = value;
                return;
            }
        }
        // 使用当前的first节点创建新的节点,并将创建之后的节点赋值给新的first首节点,
        first = new Node(key, value, first);
        number++;
    }

    /**
     * 删除给定的key键值.
     * @param key
     */
    public void deleteKey(Key key) {
        first = deleteKey(first, key);
    }

    /**
     * 删除某节点内容的部分.
     * @param x
     * @param key
     * @return
     */
    private Node deleteKey(Node x, Key key) {
        if(x == null)   return null;
        if(key.equals(x.key)) {
            number--;
            return x.next;
        }
        x.next = deleteKey(x.next, key);
        return x;
    }

    /**
     * return all keys as an Iterable.
     * @return
     */
    public Iterable<Key> keys() {
        Queue<Key> queue = new ArrayDeque<>();
        for(Node x = first; x != null; x = x.next) {
            queue.add(x.key);
        }
        return queue;
    }

    public static void main(String[] args) {
        SequentialSearchTable<String, Integer> sortTable = new SequentialSearchTable<>();
    }
}
