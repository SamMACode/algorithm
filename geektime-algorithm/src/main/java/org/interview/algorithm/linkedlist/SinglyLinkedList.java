package org.interview.algorithm.linkedlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Ma
 * @date 2020/08/29
 * 单链表的插入操作（头部插入和尾部插入），在链表中删除某个节点
 */
public class SinglyLinkedList<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SinglyLinkedList.class);

    /**
     * 表示链表中的头节点head
     */
    private Node<T> headNode = null;

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
     * 将数据插入到链表的的尾部，此操作与输入的顺序相同
     *
     * @param value
     */
    public void insertToTail(T value) {
        Node<T> newNode = new Node<>(value, null);
        if (null == headNode) {
            headNode = newNode;
        } else {
            // 当要将Node插入到末位时，需先找到最后一个节点，然后将最后一个节点的next指针指向newNode
            Node<T> pointer = headNode;
            while (null != pointer.next) {
                pointer = pointer.next;
            }
            pointer.next = newNode;
        }
    }

    /**
     * 打印出链表中所有的元素内容
     */
    public void printAllElements() {
        List<Object> allElements = new ArrayList<>();
        Node pointer = headNode;
        while (pointer != null) {
            allElements.add(pointer.data);
            pointer = pointer.next;
        }
        LOGGER.info("all elements in SinglyLinkedList is: {}", allElements.toString());
    }

    /**
     * 定义单链表Node节点，包括数据对象和next指针
     */
    public static class Node<T> {
        private T data;
        private Node next;

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }

        public T getData() {
            return this.data;
        }
    }

    /**
     * 从链表中删除某个node节点(前一个node的next指针指向当前node.next节点)
     *
     * @param node
     */
    public void deleteByNode(Node<T> node) {
        if (null == node || headNode == null) {
            return;
        }
        Node pointer = headNode;
        /*
         * pointer指针一直向前找，直到找到要删除节点的prev节点（找到后将prev.next -> node.next）
         */
        while (null != pointer && pointer.next != node) {
            pointer = pointer.next;
        }
        if (pointer == null) {
            return;
        }
        pointer.next = node.next;
    }

    /**
     * 根据value数值查找相应的Node节点
     *
     * @param value
     * @return
     */
    public Node<T> findByValue(T value) {
        if (null == headNode) {
            return null;
        }
        Node<T> pointer = headNode;
        while (pointer.data != value) {
            pointer = pointer.next;
        }
        return pointer;
    }

    /**
     * 判断链表是否为回文链表 [a, c, b, c, a]
     *
     * @return
     */
    public boolean isPalindromeList() {
        if (null == headNode) {
            return false;
        } else {
            // 开始执行找中间节点，两个指针分别从head指针开始寻找中间结点
            Node<T> pointer = headNode;
            Node<T> pointer2Step = headNode;
            if (pointer.next == null) {
                LOGGER.info("there is only one element in linkedlist");
                return true;
            }
            while (pointer2Step.next != null && pointer2Step.next.next != null) {
                pointer = pointer.next;
                pointer2Step = pointer2Step.next.next;
            }
            LOGGER.info("the middle node in linkedlist is {}", pointer.data);
            /*
             * 开始执行奇数结点的回文判断，若pointer2Step.next为null 则pointer指向的一定为整个链表的中间点
             */
            Node<T> leftLink = null;
            Node<T> rightLink = null;
            if (pointer2Step.next == null) {
                rightLink = pointer.next;
                leftLink = inverseLinkList(pointer).next;
                LOGGER.info("first node in left side is: [{}], first node in right side is: [{}]", leftLink.data,
                        rightLink.data);
            } else {
                // pointer和pointer.next指向的均为中间结点（链表数量为偶数）
                rightLink = pointer.next;
                leftLink = inverseLinkList(pointer);
            }
            return trueOrFalseResult(leftLink, rightLink);
        }
    }

    /**
     * 判断左边和右边Link是否相等(从左结点和右结点开始 分别拿相应的data值进行比较)
     *
     * @param leftNode
     * @param rightNode
     * @return
     */
    public boolean trueOrFalseResult(Node<T> leftNode, Node<T> rightNode) {
        boolean isEqual = true;
        while (leftNode != null && rightNode != null) {
            LOGGER.info("leftNode value is: [{}] rightNode value is: [{}]", leftNode.data, rightNode.data);
            if (leftNode.data == rightNode.data) {
                leftNode = leftNode.next;
                rightNode = rightNode.next;
                continue;
            } else {
                isEqual = false;
                break;
            }
        }
        return isEqual;
    }

    /**
     * 对链表内容进行反转（从当前的pointer到head结点）
     *
     * @param pointer
     * @return
     */
    public Node<T> inverseLinkList(Node<T> pointer) {
        Node<T> prevNode = null;
        Node<T> reverseNode = headNode;

        Node<T> next = null;
        while (reverseNode != pointer) {
            next = reverseNode.next;
            // 对链表进行reverse反转，将指针的方向进行改变（方向改变后，prevNode、reverseNode都向前移动一个位置）
            reverseNode.next = prevNode;
            prevNode = reverseNode;
            reverseNode = next;
        }
        // 在while循环中，reverseNode.next指向的是输入的pointer，需要进行反转
        reverseNode.next = prevNode;
        return reverseNode;
    }

    /*public static void main(String[] args) {
        SinglyLinkedList<String> singlyLinkedList = new SinglyLinkedList<>();
        singlyLinkedList.insertToHead("linkedlist");
        singlyLinkedList.insertToHead("insert");
        singlyLinkedList.insertToHead("header");
        // [main] INFO linkedlist.SinglyLinkedList  - all elements in SinglyLinkedList is: [header, insert, linkedlist]
        singlyLinkedList.printAllElements();

        // [main] INFO  org.interview.algorithm.linkedlist.SinglyLinkedList  - all elements in SinglyLinkedList is: [header, insert, linkedlist, node, value]
        singlyLinkedList.insertToTail("node");
        singlyLinkedList.insertToTail("value");
        singlyLinkedList.printAllElements();

        // 根据value值在单链表中查找对应的Node节点，然后调用deleteByNode进行删除
        Node<String> linkNode = singlyLinkedList.findByValue("insert");
        singlyLinkedList.deleteByNode(linkNode);
        // [main] INFO  org.interview.algorithm.linkedlist.SinglyLinkedList  - all elements in SinglyLinkedList is: [header, linkedlist, node, value]
        singlyLinkedList.printAllElements();

        // 判断单链表是否为回文链表（先找到中间结点，然后从中间结点开始逐个对元素进行比较）
//        String[] data = {"insert", "node", "value", "node", "insert"};
        String[] data = {"insert", "node", "node", "insert"};
        SinglyLinkedList<String> palindromeList = new SinglyLinkedList<>();
        Arrays.stream(data).forEach(value -> palindromeList.insertToTail(value));
        LOGGER.info("whether palindromeList is palindrome: [{}]", palindromeList.isPalindromeList());
    }*/

}
