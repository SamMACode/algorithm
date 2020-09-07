package org.interview.algorithm.stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Ma
 * @date 2020/08/30
 * 基于数组的Stack数据结构，支持入栈和出栈操作
 */
public class ArrayStack<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArrayStack.class);

    /**
     * 保存stack中的所有元素
     */
    private T[] data;

    /**
     * stack中当前元素的数量
     */
    private int itemCount;

    /**
     * stack的容量大小
     */
    private int capacity;

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        this.data = (T[]) new Object[capacity];
        this.itemCount = 0;
    }

    /**
     * 将元素压入stack数据栈，当没有足够空间时直接返回false
     *
     * @param item
     */
    public boolean pushItem(T item) {
        // 当itemSize大小与capacity相同时，此时items数组已经没有足够的空间，返回false
        if (itemCount == capacity) {
            return false;
        }
        // 将最新的元素放入到itemCount索引处的位置，同时对itemCount数量进行累加
        data[itemCount] = item;
        itemCount++;
        return true;
    }

    /**
     * 出栈操作 从当前data集合中弹出所有的元素
     *
     * @return
     */
    public T popItem() {
        // 若栈为空时，则直接返回null
        if (itemCount == 0) {
            return null;
        }
        // 返回下标为count-1的数组元素，并且栈中元素个数count减一
        T tempValue = data[itemCount - 1];
        --itemCount;
        return tempValue;
    }

    /**
     * 用于打印出stack中所有的元素
     *
     * @return
     */
    public String printAllItems() {
        List<T> allItems = new ArrayList<>();
        for (Integer stackItem = 0; stackItem < itemCount; stackItem++) {
            allItems.add(data[stackItem]);
        }
        return allItems.toString();
    }

    /*public static void main(String[] args) {
        ArrayStack<String> arrayStack = new ArrayStack<>(8);
        arrayStack.pushItem("array");
        arrayStack.pushItem("stack");
        arrayStack.pushItem("graphx");
        // 通过popItem方法弹栈顶元素，并将其在console上输出: popItem from arrayStack value is: [graphx]
        String popItem = arrayStack.popItem();
        LOGGER.info("popItem : [{}] from arrayStack full value is: {}", popItem, arrayStack.printAllItems());
    }*/

}
