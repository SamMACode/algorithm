package org.interview.algorithm.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Sam Ma
 * @date 2020/09/22
 * 使用Tree结构实现二分查找的功能
 */
public class BinarySearchTree {

    private static final Logger logger = LoggerFactory.getLogger(BinarySearchTree.class);

    private Node tree;

    /**
     * 从树的根结点开始查找数据，若找到则返回Node结点
     *
     * @param data
     * @return
     */
    public Node findValue(int data) {
        Node pointer = tree;
        while (pointer != null) {
            // 在二叉树中pointer的left结点值 < right结点值
            if (data < pointer.data) pointer = pointer.left;
            else if (data > pointer.data) pointer = pointer.right;
            else return pointer;
        }
        return null;
    }

    /**
     * 将输入插入到二叉树中
     *
     * @param data
     */
    public void insertValue(int data) {
        // 当跟结点tree为空时，直接将data数据写入到Node结点
        if (null == tree) {
            tree = new Node(data);
            return;
        }

        Node pointer = tree;
        // 在二叉树中插入数据的思路为：比较data数值与pointer数值大小，若小于则将其放到pointer第一个非空left node
        while (pointer != null) {
            if (data > pointer.data) {
                if (pointer.right == null) {
                    pointer.right = new Node(data);
                    return;
                }
                pointer = pointer.right;
            } else {
                // 当data的数值 < pointer.data的value值
                if (pointer.left == null) {
                    pointer.left = new Node(data);
                    return;
                }
                pointer = pointer.left;
            }
        }
    }

    /**
     * 根据数值data从二叉树中删除结点
     *
     * @param data
     */
    public void deleteNodeByValue(int data) {
        Node pointer = tree;
        Node pPointer = null;
        // 通过pointer及pPointer(父结点)指针在整个二叉树中查找data对应的Node结点，pPointer指向data的父结点
        while (pointer != null && pointer.data != data) {
            pPointer = pointer;
            if (data > pointer.data) pointer = pointer.right;
            else pointer = pointer.left;
        }
        // 当在二叉树中没有找到相应结点时，对操作流程直接进行返回
        if (null == pointer) return;

        // 分情况讨论，当pointer同时存在左子结点和右子结点，则取右子数中的最小结点替换pointer指向的结点
        if (null != pointer.left && null != pointer.right) {
            Node minP = pointer.right;
            // minParent表示minPointer的父结点，右子树的最小结点为(左边子结点->左子结点)
            Node minPP = pointer;
            while (minP.left != null) {
                minPP = minP;
                minP = minP.left;
            }

            // 将minP的数据放入到pointer指向的结点中(数据交换)，之后删除minP结点
            pointer.data = minP.data;
            pointer = minP;
            pPointer = minPP;
        }

        // 当删除的结点是叶子结点或者仅有一个子结点时 (在删除时会使用child结点直接进行替换)
        Node child = null;
        if (pointer.left != null) child = pointer.left;
        else if (pointer.right != null) child = pointer.right;

        if (pPointer == null) tree = child;
        else if (pPointer.left == pointer) pPointer.left = child;
        else pPointer.right = child;
    }

    /**
     * 从二叉树中findMin查找最小结点，是取tree根结点的左子树结点
     *
     * @return
     */
    public Node findMinNode() {
        if (null == tree) return null;
        Node pointer = tree;
        while (pointer.left != null) {
            pointer = pointer.left;
        }
        return pointer;
    }

    /**
     * 从二叉树中findMax查找最大结点，是取tree根结点的右子树结点
     *
     * @return
     */
    public Node findMaxNode() {
        if (null == tree) return null;
        Node pointer = tree;
        while (pointer.right != null) {
            pointer = pointer.right;
        }
        return pointer;
    }

    /**
     * 定义Node结点，其结构包含left左子树、right右子数及数据区内容
     */
    public static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    /*public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        // 将values数组中的数据插入到BinarySearchTree中，分别调用接口查询相关数据
        Integer[] values = new Integer[]{3, 9, 8, 7, 6, 6, 0, 1};
        Arrays.stream(values).forEach(item -> tree.insertValue(item));
        // 0    [main] INFO  org.interview.algorithm.tree.BinarySearchTree  - maxValue in BinarySearchTree is: 9, minValue is [0]
        Node maxNode = tree.findMaxNode();
        logger.info("maxValue in BinarySearchTree is: {}, minValue is {}", maxNode.data, tree.findMinNode().data);
    }*/

}
