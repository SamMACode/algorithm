package org.interview.algorithm;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * 定义有序符号表单的接口API内容,a ordered symbol table
 */
public class SymbolTable<Key extends Comparable<Key>, Value> implements Iterable<Key> {

    /**
     * 使用TreeMap作为符号表的底层数据结构
     */
    private TreeMap<Key, Value> symbolTable;

    public SymbolTable() {
        this.symbolTable = new TreeMap<>();
    }

    /**
     * 当value的值为空的时候,会将参数传递的key的值从Tree中进行移除
     *
     * @param key
     * @param value
     */
    public void putItem(Key key, Value value) {
        if (value == null) {
            symbolTable.remove(key);
        } else {
            symbolTable.put(key, value);
        }
    }

    /**
     * 返回键值对中该key对应的值的内容,符号表中是不存在null值得
     *
     * @param key
     * @return
     */
    public Value getValue(Key key) {
        return symbolTable.get(key);
    }

    /**
     * 从符号表中删除给定的key值,当Key不存在的时候直接返回null
     *
     * @param key
     * @return
     */
    public Value delete(Key key) {
        return symbolTable.remove(key);
    }

    /**
     * 给定的key是否存在于符号表中,如果存在返回True否则返回false
     *
     * @param key
     * @return
     */
    public boolean contains(Key key) {
        return symbolTable.containsKey(key);
    }

    /**
     * 返回符号表中元素的个数
     *
     * @return
     */
    public int size() {
        return symbolTable.size();
    }

    /**
     * 返回符号表中所有Key的集合
     */
    public Iterable<Key> keys() {
        return symbolTable.keySet();
    }

    /**
     * 返回符号表中所有Key的集合主要用于foreach迭代
     */
    @Override
    public Iterator<Key> iterator() {
        return symbolTable.keySet().iterator();
    }

    /**
     * return thd smallest key in the table
     */
    public Key minKey() {
        return symbolTable.firstKey();
    }

    /**
     * 返回符号表中最大的key的值
     */
    public Key maxKey() {
        return symbolTable.lastKey();
    }

    /**
     * 返回至少比key大的元素内容.(ceil)也有天花板的意思.findKey >= key
     *
     * @param key
     * @return
     */
    public Key ceil(Key key) {
        SortedMap<Key, Value> tail = symbolTable.tailMap(key);
        if (tail.isEmpty()) return null;
        else return tail.firstKey();
    }

    /**
     * Return the largest key in the table <= k
     *
     * @param key
     * @return
     */
    public Key floor(Key key) {
        if (symbolTable.containsKey(key)) return key;
        SortedMap<Key, Value> head = symbolTable.headMap(key);
        if (head.isEmpty()) return null;
        else return head.lastKey();
    }

    /*public static void main(String[] args) {
        SymbolTable<String, String> symbolTable = new SymbolTable<String, String>();
        // insert some key-value pairs
        symbolTable.putItem("www.cs.princeton.edu", "128.112.136.11");
        symbolTable.putItem("www.princeton.edu", "128.112.130.211");
        symbolTable.putItem("www.math.princeton.edu", "128.112.18.11");
        symbolTable.putItem("www.yale.edu", "130.132.51.8");
        symbolTable.putItem("www.amazon.com", "207.171.163.90");
        symbolTable.putItem("www.simpsons.com", "209.123.16.34");
        symbolTable.putItem("www.stanford.edu", "171.67.16.120");
        symbolTable.putItem("www.google.com", "64.233.161.99");
        symbolTable.putItem("www.ibm.com", "129.42.16.99");
        symbolTable.putItem("www.apple.com", "17.254.0.91");
        symbolTable.putItem("www.slashdot.com", "66.35.250.150");
        symbolTable.putItem("www.whitehouse.gov", "204.153.49.136");
        symbolTable.putItem("www.espn.com", "199.181.132.250");
        symbolTable.putItem("www.snopes.com", "66.165.133.65");
        symbolTable.putItem("www.movies.com", "199.181.132.250");
        symbolTable.putItem("www.cnn.com", "64.236.16.20");
        symbolTable.putItem("www.iitb.ac.in", "202.68.145.210");
    }*/

}
