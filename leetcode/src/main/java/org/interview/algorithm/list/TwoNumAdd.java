package org.interview.algorithm.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Ma
 * @date 2021/04/21
 * 求两数字之和leetcode https://leetcode-cn.com/problems/add-two-numbers/solution/
 */
public class TwoNumAdd {

    /* 给定两个非空链表来表示两个非负整数。位数按照逆序方式存储，它们的每个节点只存储单个数字。将两数相加返回一个新的链表:
     输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     输出：7 -> 0 -> 8
     原因：342 + 465 = 807 */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        // carry表示进位数
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            // 表示两位置计算完成后，进位的大小（没问题每次都会加carry字段）
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) {p = p.next;}
            if (q != null) {q = q.next;}
        }
        if (carry > 0) {curr.next = new ListNode(carry);}
        return dummyHead.next;
    }

    /*public static void main(String[] args) {
        ListNode a3 = new ListNode(3);
        ListNode a4 = new ListNode(4);
        ListNode a2 = new ListNode(2);
        a2.next = a4;
        a4.next = a3;

        ListNode b4 = new ListNode(4);
        ListNode b6 = new ListNode(6);
        ListNode b5 = new ListNode(5);
        b5.next = b6;
        b6.next = b4;

        TwoNumAdd algo = new TwoNumAdd();
        ListNode head = algo.addTwoNumbers(a2, b5);
        List<Integer> arrays = new ArrayList<>();
        // 将计算之后结果进行输出, 在console显示的数据为[7, 0, 8]
        while (null != head) {
            arrays.add(head.val);
            head = head.next;
        }
        System.out.println(arrays);
    }*/

}

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int x) { val = x; }
}
