package org.interview.search;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @description: 定义有序符号表单的接口API内容,a ordered symbol table.
 *    该接口支持通常情况下的put,set,remove以及get操作.同时它也提供了对元素进行排序查找的方法.
 *   在该符号表中不允许key的键值为Null,当调用get方法获取到一个不存在key的值的时候,会返回Null.
 *
 * @author dong
 * @create 2018-08-12 下午12:46
 **/
@Slf4j
public class ST<Key extends Comparable<Key>, Value> implements Iterable<Key> {

    // 使用TreeMap作为符号表的底层数据结构.
    private TreeMap<Key, Value> st;

    public ST() {
        this.st = new TreeMap<Key, Value>();
    }

    /**
     * @desc: 当value的值为空的时候,会将参数传递的key的值从Tree中进行移除.
     * */
    public void put(Key key, Value value) {
        if(value == null) {
            st.remove(key);
        } else {
            st.put(key, value);
        }
    }

    /**
     * 返回键值对中该key对应的值的内容,符号表中是不存在null值得.
     * */
    public Value get(Key key) {
        return st.get(key);
    }

    /**
     * 从符号表中删除给定的key值,当Key不存在的时候直接返回null.
     * */
    public Value delete(Key key) {
        return st.remove(key);
    }

    /**
     * 给定的key是否存在于符号表中,如果存在返回True否则返回false.
     * */
    public boolean contains(Key key) {
        return st.containsKey(key);
    }

    /**
     * 返回符号表中元素的个数.
     * */
    public int size() {
        return st.size();
    }

    /**
     * 返回符号表中所有Key的集合.
     * */
    public Iterable<Key> keys() {
        return st.keySet();
    }

    /**
     * 返回符号表中所有Key的集合主要用于foreach迭代.
     * */
    @Override
    public Iterator<Key> iterator() {
        return st.keySet().iterator();
    }

    /**
     * return thd smallest key in the table.
     * */
    public Key min() {
        return st.firstKey();
    }

    /**
     * 返回符号表中最大的key的值.
     * */
    public Key max() {
        return st.lastKey();
    }

    /**
     * 返回至少比key大的元素内容.(ceil)也有天花板的意思.
     * findKey >= key
     * */
    public Key ceil(Key key) {
        SortedMap<Key, Value> tail = st.tailMap(key);
        if(tail.isEmpty()) return null;
        else return tail.firstKey();
    }

    /**
     * Return the largest key in the table <= k.
     * */
    public Key floor(Key key) {
        if(st.containsKey(key)) return key;
        SortedMap<Key, Value> head = st.headMap(key);
        if(head.isEmpty()) return null;
        else return head.lastKey();
    }

    public static void main(String[] args) {
        ST<String, String> st = new ST<String, String>();
        // insert some key-value pairs
        st.put("www.cs.princeton.edu",   "128.112.136.11");
        st.put("www.cs.princeton.edu",   "128.112.136.35");    // overwrite old value
        st.put("www.princeton.edu",      "128.112.130.211");
        st.put("www.math.princeton.edu", "128.112.18.11");
        st.put("www.yale.edu",           "130.132.51.8");
        st.put("www.amazon.com",         "207.171.163.90");
        st.put("www.simpsons.com",       "209.123.16.34");
        st.put("www.stanford.edu",       "171.67.16.120");
        st.put("www.google.com",         "64.233.161.99");
        st.put("www.ibm.com",            "129.42.16.99");
        st.put("www.apple.com",          "17.254.0.91");
        st.put("www.slashdot.com",       "66.35.250.150");
        st.put("www.whitehouse.gov",     "204.153.49.136");
        st.put("www.espn.com",           "199.181.132.250");
        st.put("www.snopes.com",         "66.165.133.65");
        st.put("www.movies.com",         "199.181.132.250");
        st.put("www.cnn.com",            "64.236.16.20");
        st.put("www.iitb.ac.in",         "202.68.145.210");

    }
}
