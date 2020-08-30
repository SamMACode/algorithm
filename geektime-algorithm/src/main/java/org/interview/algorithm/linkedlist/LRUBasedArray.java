package org.interview.algorithm.linkedlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sam Ma
 * @date 2020/08/23
 * 使用数组Array实现lru算法，最近未被访问的元素会被移除
 */
public class LRUBasedArray<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LRUBasedArray.class);

    private static final Integer DEFAULT_CAPACITY = (1 << 3);

    // lru缓存数组的大小
    private int capacity;

    // 表示lru中缓存的数据
    private T[] cachedData;

    // 表示data数组中实际缓存元素的个数
    private int itemCount;

    // holder作用在于快速定位元素的index,不必从头到位遍历数据找到匹配的元素(较关键)
    private Map<T, Integer> holder;

    public LRUBasedArray() {
        this(DEFAULT_CAPACITY);
    }

    public LRUBasedArray(int capacity) {
        this.capacity = capacity;
        this.cachedData = (T[]) new Object[capacity];
        this.itemCount = 0;
        this.holder = new HashMap<>(capacity);
    }

    /**
     * lru访问元素：当object元素未缓存时，将其放入数组首位。否则，已经访问过的元素放在数组首位，其它元素后移
     * @param object
     */
    public void visitItem(T object) {
        if (null == object) {
            throw new IllegalArgumentException("null value isn't supported");
        }
        Integer index = holder.get(object);
        if (index == null) {
            if (isFull()) {
                removeAndCache(object);
            } else {
                cacheValue(object, itemCount);
            }
        } else {
            updateCacheValue(index);
        }
    }

    /**
     * 当前lru cache队列已经满了，当cache已满时需移除队列末尾元素
     * @return
     */
    public boolean isFull() {
        return this.capacity == itemCount;
    }

    /**
     * cache队列已满时，移除最后元素同时将cacheItem放入首部
     * @param cacheItem
     */
    public void removeAndCache(T cacheItem) {
        T expiredItem = cachedData[--itemCount];
        holder.remove(expiredItem);
        cacheValue(cacheItem, itemCount);
    }

    /**
     * 将要访问的元素放入到lru缓存队列中，同时在holder添加对象引用
     * @param cacheItem
     * @param end
     */
    public void cacheValue(T cacheItem, int end) {
        rightShift(end);
        cachedData[0] = cacheItem;
        holder.put(cacheItem, 0);
        itemCount ++;
    }

    /**
     * end左边的数据统一右移动一位（补充当前访问元素位置），当前访问元素放置在index 0处
     * @param end
     */
    public void rightShift(int end) {
        for (int i = end - 1; i >= 0; i--) {
            cachedData[i + 1] = cachedData[i];
            holder.put(cachedData[i], i + 1);
        }
    }

    /**
     * 若访问的元素item之前已经存在与lru中，则更新其位置于position 0
     * @param index
     */
    public void updateCacheValue(int index) {
        T targetValue = cachedData[index];
        rightShift(index);
        cachedData[0] = targetValue;
        holder.put(targetValue, 0);
    }

    /**
     * 将当前cachedData中的所有元素转换未字符串
     * @return
     */
    public String printAllItems() {
        return Arrays.toString(this.cachedData);
    }

    /*public static void main(String[] args) {
        LRUBasedArray<String> lruBasedArray = new LRUBasedArray<>();
        lruBasedArray.visitItem("geektime");
        lruBasedArray.visitItem("algorithm");
        lruBasedArray.visitItem("scala");
        // [main] INFO linkedList.LRUBasedArray  - lruBasedArray all cached value [scala, algorithm, geektime, null]
        LOGGER.info("lruBasedArray all cached value {}", lruBasedArray.printAllItems());
        lruBasedArray.visitItem("language");
        // 重复访问已经缓存的scala元素，观察其是否会出现在数组位置0处, [main] INFO - revisited scala item [scala, language, algorithm, geektime, null, null, null, null]
        lruBasedArray.visitItem("scala");
        LOGGER.info("revisited scala item {}", lruBasedArray.printAllItems());

        // 向lruBasedArray中放入足够多元素，当cache array满时是否会淘汰最后元素
        lruBasedArray.visitItem("kubernetes");
        lruBasedArray.visitItem("apache spark graphx");
        lruBasedArray.visitItem("apache hadoop");
        lruBasedArray.visitItem("spring cloud architecture");
        lruBasedArray.visitItem("tcp/ip illustrated");
        // lru cached value: [tcp/ip illustrated, spring cloud architecture, apache hadoop, apache spark graphx, kubernetes, scala, language, algorithm]
        LOGGER.info("cached array is full, geektime is expired, lru cached value: {}", lruBasedArray.printAllItems());

    }*/

}
