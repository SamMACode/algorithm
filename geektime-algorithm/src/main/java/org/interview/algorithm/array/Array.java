package org.interview.algorithm.array;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Sam Ma
 * @date 2020/08/23
 * 使用Java实现数组操作：数组插入元素、从数组中删除元素和随机访问操作
 */
public class Array {

    private static final Logger logger = LoggerFactory.getLogger(Array.class);

    // 用于表示数组中的元素
    private int[] data;

    // 表示定义数组时的长度
    private int length;

    // 表示数组中实际包含元素的数量，item元素的数量总比最大索引大1
    private int itemCount;

    public Array(int capacity) {
        this.data = new int[capacity];
        this.length = capacity;
        this.itemCount = 0;
    }

    /**
     * 根据指定下标索引向数组中插入元素（索引之后的元素统一向后移动一个位置）
     * @param index
     * @param value
     * @return
     */
    public boolean insert(int index, int value) {
        if (itemCount == length) {
            logger.info("there are not suitable space for insert item");
            return false;
        }

        if (index < 0 || index > itemCount) {
            logger.info("invalid index of array: {}", index);
            return false;
        }

        // 将当前要插入index的元素统一向后移动一个位置，并将最新的元素放置到给定的位置，同时对数组中元素个数累加
        for (int i = itemCount; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        ++itemCount;

        return true;
    }

    /**
     * 根据下标索引删除数组中指定位置的元素（index之后的元素向前移动一个位置）
     * @param index
     * @return
     */
    public boolean delete(int index) {
        if (index < 0 || index > itemCount) {
            return false;
        }

        for (int i = index + 1; i < itemCount; i++) {
            data[i - 1] = data[i];
        }
        // 减少当前当前数据中元素的总数，同时将之前最后一个元素置为默认值0
        --itemCount;
        data[itemCount] = 0;
        return true;
    }

    /**
     * 根据下标索引获取数组指定位置的元素
     * @param index
     * @return
     */
    public int find(int index) {
        if (index < 0 || index >= itemCount) {
            return -1;
        }
        return data[index];
    }

    public String printAllItems() {
        return Arrays.toString(this.data);
    }

    /*public static void main(String[] args) {
        Array customArray = new Array(6);
        customArray.insert(0, 4);
        customArray.insert(1, 5);
        // 在array的0处插入元素5之后，实际上array元素顺序为{3, 4, 5}
        customArray.insert(0, 3);
        logger.info("custom Array are: {}", customArray.printAllItems());

        int itemValue = customArray.find(4);
        logger.info("invalid position 4 for customArray, value: {}", itemValue);

        // 删除下标索引为1的item，重新打印array数组内容为{3, 5}
        customArray.delete(1);
        logger.info("delete item at position 1, customArray value: {}", customArray.printAllItems());
    }*/

}
