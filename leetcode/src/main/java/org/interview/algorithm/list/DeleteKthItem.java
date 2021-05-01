package org.interview.algorithm.list;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sam Ma
 * @date 2021/04/30
 * 删除链表的倒数kth个节点，Leetcode:给定一个链表，删除链表的倒数第n个节点，并且返回链表的头结点。
 */
public class DeleteKthItem {

    /**
     * 移除链表中倒数第n个元素，两次遍历的方式，时间复杂度为2n
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 哑结点，用来简化某种极端情况，例如列表中只包含一个结点，或者需要删除列表的头部
        ListNode dummy = new ListNode(0);
        // 哑结点指向头部结点（有可优化的地方）
        dummy.next = head;
        // 保存链表的长度，用于计算要删除结点位置（L-n+2）
        int length = 0;
        ListNode len = head;
        while (len != null) {
            ++length;
            len = len.next;
        }

        length = length - n;
        ListNode target = dummy;
        while (length > 0) {
            target = target.next;
            length--;
        }
        // 把第(L-n)个结点的next指针重新连接至第(L-n+2)个结点
        target.next = target.next.next;
        return dummy.next;
    }

    /**
     * 使用一次遍历的方式删除链表中倒数nth结点（用快慢指针实现）
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEndWithLowCost(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        // 声明两个指向头结点的节点，node1和node2结点
        ListNode node1 = dummy, node2 = dummy;
        /*
         * node1节点先跑，node1节点跑到第n个节点的时候，node2结点开始跑：
         * 当node1节点跑到最后一个结点时，node2节点所在的位置就是第(l-n)个节点，也就是倒数第n+1（L代表总链表长度）
         */
        while (node1 != null) {
            node1 = node1.next;
            if (n < 1 && node1 != null) {
                node2 = node2.next;
            }
            n--;
        }
        node2.next = node2.next.next;
        return dummy.next;
    }

    /*public static void main(String[] args) {
        ListNode a3 = new ListNode(3);
        ListNode a4 = new ListNode(4);
        ListNode a2 = new ListNode(2);
        a2.next = a4;
        a4.next = a3;
        ListNode b6 = new ListNode(6);
        ListNode b5 = new ListNode(5);
        a3.next = b5;
        b5.next = b6;

        DeleteKthItem util = new DeleteKthItem();
        // [2 -> 4 -> 3 -> 5 -> 6], n~指定要删除元素的下标索引
//        ListNode head = util.removeNthFromEnd(a2, 2);
        // 使用fast、slow指针实现遍历链表的需求，并移除last n元素
        ListNode head = util.removeNthFromEndWithLowCost(a2, 2);
        List<Integer> array = new LinkedList<>();
        while (head != null) {
            array.add(head.val);
            head = head.next;
        }
        // 将反转后的array数组打印在console上
        System.out.println("reversed value: " + array);
    }*/

}
