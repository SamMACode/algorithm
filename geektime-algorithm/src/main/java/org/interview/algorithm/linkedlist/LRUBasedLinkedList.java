package org.interview.algorithm.linkedlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Ma
 * @date 2020/08/30
 * LRU算法基于单链表进行实现（最近访问的元素保持在链表的头部）
 */
public class LRUBasedLinkedList<T> {

    private final static Logger LOGGER = LoggerFactory.getLogger(LRUBasedLinkedList.class);

    /**
     * 单链表默认的长度
     */
    private final static Integer DEFAULT_CAPACITY = 5;

    private Node<T> headNode;

    /**
     * 链表的实际长度
     */
    private int length;

    /**
     * lru缓存能放置元素的数量
     */
    private int capacity;

    public LRUBasedLinkedList() {
        this.capacity = DEFAULT_CAPACITY;
        this.length = 0;
    }

    public LRUBasedLinkedList(Integer capacity) {
        this.capacity = capacity;
        this.length = 0;
    }

    /**
     * 将元素数据添加到LRUBasedLinkedList中（若已存在，则删除node结点然后重新插入到head头部，否则直接插入到头部）
     *
     * @param value
     */
    public void visitItem(T value) {
        Node<T> prevNode = findPrevNode(value);
        if (prevNode != null) {
            deleteFromPrevNode(prevNode);
            insertToHead(value);
        } else {
            // 若当前lru链表队列已满，则删除链表中的最后一个元素，之后将新节点插入到头部
            if (length == capacity) {
                deleteElementAtEnd();
            }
            insertToHead(value);
        }
        this.length++;
    }

    /**
     * 删除链表中的末尾结点，找到倒数第二个结点（将其置为末尾结点）
     */
    public void deleteElementAtEnd() {
        Node<T> pointer = headNode;
        // 若pointer指向null元素，则直接将空链表则进行返回
        if (pointer == null) {
            return;
        }

        Node<T> prevPointer = null;
        // 从headerNode结点开始，一直寻找倒数第二个结点(pointer.next连续找两次，最终prevNode也即要查找的点)
        while (pointer.next != null) {
            if (pointer.next.next != null) {
                prevPointer = pointer;
                pointer = pointer.next.next;
            } else {
                break;
            }
        }
        // 将要删除的Node结点置空，同时设置prevPointer.next值为null, 并将链表length整体减去一
        Node<T> temp = prevPointer.next;
        prevPointer.next = null;
        temp = null;
        length--;
    }

    /**
     * 从prevNode删除其next结点，
     *
     * @param prevNode
     */
    private void deleteFromPrevNode(Node<T> prevNode) {
        Node<T> tempNode = prevNode.next;
        // 将prevNode.next指向要被删除结点next指向的元素，同时将当前链表的元素数减一
        prevNode.next = tempNode.next;
        tempNode = null;
        length--;
    }

    /**
     * 将数据插入到链表的头部，此操作与输入的顺序相反
     *
     * @param value
     */
    public void insertToHead(T value) {
        Node<T> newNode = new Node<>(value, null);
        if (null == headNode) {
            headNode = newNode;
        } else {
            // 先将新创建node节点的next指针指向当前head节点，使其作为第一个node节点（head指针指向新节点）
            newNode.next = headNode;
            headNode = newNode;
        }
    }

    /**
     * 根据value数据找到其前置Node<T>结点，若存在则返回前置结点 否则返回null值
     *
     * @param value
     * @return
     */
    public Node<T> findPrevNode(T value) {
        Node<T> pointer = headNode;
        if (pointer == null) {
            return null;
        }
        while (pointer.next != null) {
            if (pointer.next.value.equals(value)) {
                return pointer;
            }
            pointer = pointer.next;
        }
        return null;
    }

    /**
     * 打印出链表中所有的元素内容
     */
    public String printAllElements() {
        List<Object> allElements = new ArrayList<>();
        Node pointer = headNode;
        while (pointer != null) {
            allElements.add(pointer.value);
            pointer = pointer.next;
        }
        return allElements.toString();
    }

    public static class Node<T> {
        private T value;
        public Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        public T getValue() {
            return value;
        }
    }

    public Node<T> getHeadNode() {
        return headNode;
    }

    public void setHeadNode(Node<T> headNode) {
        this.headNode = headNode;
    }

    /*public static void main(String[] args) {
        LRUBasedLinkedList<String> lruBasedLinkedList = new LRUBasedLinkedList<>();
        lruBasedLinkedList.visitItem("geektime");
        lruBasedLinkedList.visitItem("algorithm");
        lruBasedLinkedList.visitItem("scala");
        // org.interview.algorithm.linkedlist.LRUBasedLinkedList  - lruBasedLinkedList all cached value [scala, algorithm, geektime]
        LOGGER.info("lruBasedLinkedList all cached value {}", lruBasedLinkedList.printAllElements());
        lruBasedLinkedList.visitItem("language");
        // 重复访问已经缓存的scala元素，观察其是否会出现在数组位置0处, INFO  org.interview.algorithm.linkedlist.LRUBasedLinkedList  - revisited scala item [scala, language, algorithm, geektime
        lruBasedLinkedList.visitItem("scala");
        LOGGER.info("revisited scala item {}", lruBasedLinkedList.printAllElements());

        // 向lruBasedArray中放入足够多元素，当cache array满时是否会淘汰最后元素
        lruBasedLinkedList.visitItem("kubernetes");
        lruBasedLinkedList.visitItem("graphx");
        LOGGER.info("cached array is full, geektime is expired, lru cached value: {}", lruBasedLinkedList.printAllElements());
    }*/

}
