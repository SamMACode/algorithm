package org.interview.search.SymbolTables;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 使用链表的方式实现符号表
 *
 * @author dong
 * @create 2018-08-12 下午3:17
 **/
public class SequentialSearchST<Key, Value> {
    private int number;      // 用于统计符号表中的元素的个数.
    private Node first;      // 定义链表中的头结点.

    // a helper linked list data type.
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

    // 返回符号表中的所有元素的个数.
    public int size() {
        return number;
    }
    // is the symbol table empty
    public boolean isEmpty() {
        return size() == 0;
    }
    // does this symbol table contain the given key?
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     * @desc: 根据给定的Key值返回其对应的Value值.
     * @param key 返回给定key对应的value值,当key不存在的时候直接返回Nulll.
     * */
    public Value get(Key key) {
        // 使用的是从头指针迭代遍历所有的内容,直到到达尾节点为null.
        for(Node x = first; x != null; x = x.next) {
            if(key.equals(x.key))   return x.value;
        }
        return null;
    }

    /**
     * @desc: 将给定的Key.value键值对添加到符号表中,如果key的值为Null则直接删除元素.
     * @param key
     * @param value value
     * */
    public void put(Key key, Value value) {
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
        number++;   // 当插入了新的节点Node元素之后,需要将number元素数量+!.
    }

    /**
     * @desc:删除给定的key键值.
     * @param key
     * */
    public void deleteKey(Key key) {
        first = deleteKey(first, key);
    }

    /**
     * @desc:删除某节点内容的部分.
     * @param x 传递整个链表中的first首节点,用于遍历查找内容.
     * @param key
     * */
    private Node deleteKey(Node x, Key key) {
        if(x == null)   return null;
        if(key.equals(x.key)) {
            number--;              // 删除掉元素之后将元素的数量--
            // {返回的为x.next}表示返回Next指针指向的元素,按道理来说应该返回的是当前Node对象的内容.
            return x.next;         // 返回当前要删除Node元素的next指针指向的地方.
        }
        // 使用了递归调用的方式对链表进行遍历,直到找到要被删除的节点删除之后进行返回.
        x.next = deleteKey(x.next, key);
        return x;
    }

    // return all keys as an Iterable.
    public Iterable<Key> keys() {
        Queue<Key> queue = new ArrayDeque<Key>();
        for(Node x = first; x != null; x = x.next) {
            queue.add(x.key);
        }
        return queue;
    }

    public static void main(String[] args) {
        SequentialSearchST<String, Integer> st = new SequentialSearchST<String, Integer>();
        /*for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));*/
    }
}
