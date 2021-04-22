package org.interview.algorithm.list;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sam Ma
 * @date 2021/04/21
 * 翻转链表需求，剑指offer:输入一个链表，反转链表后，输出链表的所有元素
 */
public class ReverseLinkList {

    /**
     * 翻转一个链表head和tail翻转，调整next到反方向
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode next = null;
        ListNode pre = null;

        while (head != null) {
            // 保存要反转到头的那个节点（每次翻转就这两个步骤）
            next = head.next;
            // 要反转的那个节点指向已经反转的上一个节点（备注，第一次反转的时候会指向null）
            head.next = pre;
            // 上一个已经反转到头部的节点（进入到下一次迭代中）
            pre = head;
            // 一直向链表尾部走
            head = next;
        }
        return pre;
    }

    /*public static void main(String[] args) {
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(3);
        ListNode d = new ListNode(4);
        ListNode e = new ListNode(5);
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        new ReverseLinkList().reverseList(a);
        List<Integer> array = new LinkedList<>();
        while (e != null) {
            array.add(e.val);
            e = e.next;
        }
        // 将反转后的array数组打印在console上
        System.out.println("reversed value: " + array);
    }*/

}