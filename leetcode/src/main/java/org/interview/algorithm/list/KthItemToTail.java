package org.interview.algorithm.list;

/**
 * @author Sam Ma
 * @date 2021/04/23
 * 链表中倒数第k个节点，题目描述: 输入一个链表，输出该链表中倒数第k个结点
 */
public class KthItemToTail {

    /*
     * 时间复杂度，一次性就可以遍历完；首先两个节点/指针，一个节点 node1 先开始跑，指针 node1 跑到 k-1个节点后，另一个节点 node2 开始跑，
     * 当 node1 跑到最后时，node2 所指的节点就是倒数第k个节点也就是正数第(L-K+1)个节点。
     */
    public ListNode findKthToTail(ListNode head, int k) {
        // 如果链表为空或者k小于等于0，则直接返回null元素
        if (head == null || k <= 0) {
            return null;
        }
        // 声明两个指向头结点的节点, 及当前节点的个数
        ListNode node1 = head, node2 = head;
        int count = 0;
        // 记录k值，后面要使用（kth元素表示，倒数第n个元素）
        int index = k;
        while (node1 != null) {
            node1 = node1.next;
            count++;
            if (k < 1) {
                node2 = node2.next;
            }
            k--;
        }

        // 若节点个数小于所求的倒数第k个节点，则返回空
        if (count < index) {
            return null;
        }
        return node2;
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

        // 从KthItemToTail中获取查找倒数第kth个元素，并进行返回（数据：2 -> 4 -> 3 -> 5 -> 6）
        KthItemToTail util = new KthItemToTail();
        ListNode kthItem = util.findKthToTail(a2, 4);
        System.out.println("kthItem value: " + kthItem.val);
    }*/

}
