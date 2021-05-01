package org.interview.algorithm.list;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sam Ma
 * @date 2021/05/01
 * 合并两个排序链表，度小满金融二面手写的算法题(自己当时的实现较复杂)
 */
public class MergeTwoList {

    /**
     * 合并两个有序链表list1和list2，使其最终整体有序
     *
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoList(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.val <= list2.val) {
            list1.next = mergeTwoList(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoList(list1, list2.next);
            return list2;
        }
    }

    /*public static void main(String[] args) {
        ListNode a3 = new ListNode(3);
        ListNode a4 = new ListNode(4);
        ListNode head1 = new ListNode(2);
        head1.next = a3;
        a3.next = a4;

        ListNode b6 = new ListNode(6);
        ListNode head2 = new ListNode(5);
        head2.next = b6;

        MergeTwoList util = new MergeTwoList();
        // two linkedlist value: [2, 3, 4, 5, 6]
        ListNode header = util.mergeTwoList(head1, head2);
        List<Integer> array = new LinkedList<>();
        while (header != null) {
            array.add(header.val);
            header = header.next;
        }
        // 将反转后的array数组打印在console上
        System.out.println("merge two linkedlist value: " + array);
    }*/

}
