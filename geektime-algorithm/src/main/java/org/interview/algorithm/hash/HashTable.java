package org.interview.algorithm.hash;

/**
 * @author Sam Ma
 * @date 2020/09/29
 * 散列表HashTable数据结构算法实现 (自定义hash函数)
 */
public class HashTable<K, V> {

    /**
     * 定义HashTable表初始化容量大小
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 8;

    /**
     * 定义装载因子大小0.75
     */
    private static final float LOAD_FACTOR = 0.75f;

    /**
     * 初始化散列表数组
     */
    private Entry<K, V>[] table;

    /**
     * 散列表实际存储元素数量
     */
    private int size;

    /**
     * 散列表索引数量
     */
    private int used;

    public HashTable() {
        this.table = (Entry<K, V>[]) new Entry[DEFAULT_INITIAL_CAPACITY];
    }

    /**
     * 向HashTable中新增key,Value数据
     *
     * @param key
     * @param value
     */
    public void putValue(K key, V value) {
        int index = hash(key);
        // 位置未被引用，创建哨兵结点
        if (table[index] == null) {
            table[index] = new Entry<>(null, null, null);
        }

        Entry<K, V> temp = table[index];
        // 向hashTable中新增节点，当HashTable中被占用元素超过负载时，对hashTable进行动态扩容
        if (temp.next == null) {
            temp.next = new Entry<>(key, value, null);
            size++;
            used++;
            if (used >= table.length * LOAD_FACTOR) {

            }
        }
    }

    /**
     * 散列函数，参考hashmap散列函数
     *
     * @param key
     * @return
     */
    private int hash(Object key) {
        int value;
        return (key == null) ? 0 : ((value = key.hashCode()) ^ (value >>> 16)) % table.length;
    }

    static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
