package org.interview.algorithm.linkedlist;

import org.interview.algorithm.linkedlist.LRUBasedLinkedList.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sam Ma
 * @date 2020/08/30
 * 链表相关的算法：单链表的反转、链表中环的检测、两个有序链表的合并、求链表的中间结点
 */
public class LinkedListAlgo {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkedListAlgo.class);

    /**
     * 对单链表进行反转（reverse linkedlist）
     *
     * @param header
     */
    public static Node reverseLinkedList(Node header) {
        Node current = header, prev = null;
        while (current != null) {
            Node next = current.next;
            // 将当前Node的next指针指向prev结点，然后将prev和current分别向前移动一个Node位置
            current.next = prev;
            prev = current;
            current = next;
        }
        // 最终current为null 此时prev指向的是原链表中的最后一个Node结点，此时其应该为反转后的head结点
        return prev;
    }

    /**
     * 检查链表中是否存在环circle，fast和slow最终是否会指向同一个结点
     *
     * @param list
     * @return
     */
    public static boolean checkCircle(Node list) {
        if (list == null) {
            return false;
        }
        Node fast = list.next;
        Node slow = list;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            // fast和slow分别从head结点开始分别遍历，fast每次2step slow每次1step，当两个pointer指向node相同时 则构成环。
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /**
     * 有序链表的合并，leetcode 21，soldier的next指针指向的为合并的首结点
     *
     * @param list1
     * @param list2
     * @return
     */
    public static Node<Integer> mergeTwoLists(Node<Integer> list1, Node<Integer> list2) {
        Node<Integer> soldier = new Node<>(0, null);
        Node<Integer> pointer = soldier;

        while (list1 != null && list2 != null) {
            // 对list1和list2当前指向的元素进行比较，pointer指向较大的Node结点，同时pointer向前迭代一个位置
            if (list1.getValue() < list2.getValue()) {
                pointer.next = list1;
                list1 = list1.next;
            } else {
                pointer.next = list2;
                list2 = list2.next;
            }
            pointer = pointer.next;
        }

        // 当list1或者list2中任意一个element为null，则需将另外一个list列表作为pointer的后续部分
        if (list1 != null) {
            pointer.next = list1;
        }
        if (list2 != null) {
            pointer.next = list2;
        }
        return soldier.next;
    }

    /**
     * 删除链表中倒数第kth个元素（思路：先找到第k个元素，然后fast slow指针同时向后走，当fast为最后一个node时slow为要被删除的点）
     *
     * @param list
     * @param kth
     * @return
     */
    public static Node deleteLastKthNode(Node list, Integer kth) {
        Node fast = list;
        Integer index = 1;
        // 从header开始先找到第kth个node的位置 (.next调用kth-1次)，并且由pointer指向该node
        while (fast != null && index < kth) {
            fast = fast.next;
            ++index;
        }
        if (fast == null) {
            return list;
        }

        // 从第kth-1个node的位置开始，slow和fast同时向前移动 直到fast指向最后一个Node结点,此时prevPointer指向被删除的前置结点
        Node slow = list;
        Node prevPointer = null;
        while (fast.next != null) {
            fast = fast.next;
            prevPointer = slow;
            slow = slow.next;
        }

        // prevPointer == null的情况为：当前list中元素数量为(kth)个 (要测试下)
        if (prevPointer == null) {
            list = list.next;
        } else {
            prevPointer.next = prevPointer.next.next;
        }
        return list;
    }

    /**
     * 求list的中间结点，思路与之前是一致的（fast pointer每次走2step，slow pointer每次走1step）
     *
     * @param list
     * @return
     */
    public static Node findMiddleNode(Node list) {
        if (null == list) {
            return null;
        }
        Node fast = list;
        Node slow = list;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    /*public static void main(String[] args) {
        LRUBasedLinkedList<String> lruBasedLinkedList = new LRUBasedLinkedList<>();
        lruBasedLinkedList.visitItem("geektime");
        lruBasedLinkedList.visitItem("algorithm");
        lruBasedLinkedList.visitItem("scala");
        lruBasedLinkedList.visitItem("kubernetes");

        // 使用reverseLinkedList将linkedlist进行反转，打印出所有的元素内容 (应与visitItem顺序相同 由于lru访问)
        Node header = LinkedListAlgo.reverseLinkedList(lruBasedLinkedList.getHeadNode());
        lruBasedLinkedList.setHeadNode(header);
        LOGGER.info("reverse all elements in linkedlist: {}", lruBasedLinkedList.printAllElements());

        // 在linkedList中检查链表中是否存在环circle
        boolean isCircle = checkCircle(lruBasedLinkedList.getHeadNode());
        LOGGER.info("whether exist circle in linkedlist: {}", isCircle);

        // 使用slow和fast pointer从链表中找到middle node中间结点 ("algorithm"和"scala"都算)
        Node middleNode = findMiddleNode(lruBasedLinkedList.getHeadNode());
        LOGGER.info("find middleNode from lruBasedLinkedList: {}", middleNode.getValue());

        // 从linkedlist删除倒数第2个元素，然后打印出所有元素内容，linkedlist.LinkedListAlgo  - headerNode geektime, all elements in linkedList: [geektime, algorithm, kubernetes]
        Node headerNode = deleteLastKthNode(lruBasedLinkedList.getHeadNode(), 4);
        lruBasedLinkedList.setHeadNode(headerNode);
        LOGGER.info("headerNode: [{}], all elements in linkedList: {}", headerNode.getValue(),
                lruBasedLinkedList.printAllElements());

        // 使用lruBasedLinked模拟两个有序的序列，然后对其内容进行合并(返回Node为合并后最新header指针)
        LRUBasedLinkedList<Integer> linkedList1 = new LRUBasedLinkedList<>();
        linkedList1.insertToHead(5);
        linkedList1.insertToHead(3);
        linkedList1.insertToHead(1);

        LRUBasedLinkedList<Integer> linkedList2 = new LRUBasedLinkedList<>();
        linkedList2.insertToHead(4);
        linkedList2.insertToHead(2);
        linkedList2.insertToHead(1);
        Node<Integer> mergeHeader = mergeTwoLists(linkedList1.getHeadNode(), linkedList2.getHeadNode());
        linkedList1.setHeadNode(mergeHeader);
        LOGGER.info("merge linkedlist value: {}", linkedList1.printAllElements());
    }*/

}
