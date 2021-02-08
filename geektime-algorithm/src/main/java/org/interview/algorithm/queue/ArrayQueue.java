package org.interview.algorithm.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sam Ma
 * @date 2021/02/08
 * 实现顺序队列，线程池实现的逻辑思路（非阻塞式-直接拒绝、阻塞式-将请求排队，等有空闲线程时取出排队请求继续处理）
 * 主要两个操作: 压入元素enqueue(item) 与 弹出元素dequeue()
 */
public class ArrayQueue {

    private static final Logger logger = LoggerFactory.getLogger(ArrayQueue.class);
    // 数目 items，数组大小 n
    private String[] items;
    private int n = 0;
    // head表示队头下标、tail表示队尾下标
    private int head = 0;
    private int tail = 0;

    // 申请一个大小为capacity的数组
    private ArrayQueue(int capacity) {
        items = new String[capacity];
        n = capacity;
    }

    /**
     * 将元素压入到队列中,成功返回true；否则，则返回false；
     *
     * @param item
     * @return
     */
    public boolean enqueue(String item) {
        // 如果tail的大小与n相等，则表示queue元素已满，无法再添加新的元素 return false
        if (tail == n) {
            /* tail == n && head == 0, 表示整个队列都被占满 */
            if (head == 0) return false;
            /* 空间优化，否则将queue中删除的元素向前移动, 移动完成后 重新更新head(0)和tail(tail-head)的值 */
            for (int i = head; i < tail; ++i) {
                items[i - head] = items[i];
            }
            tail -= head;
            head = 0;
        }
        items[tail] = item;
        ++tail;
        return true;
    }

    /**
     * 出队列，将元素弹出queue队列
     *
     * @return
     */
    public String dequeue() {
        // 如果head == tail 则表示queue为空，无法弹出新的元素
        if (head == tail) return null;
        String value = items[head];
        ++ head;
        return value;
    }

}
