package org.interview.algorithm.tree;

/**
 * @author Sam Ma
 * @date 2021/03/07
 * B+树非叶子节点的定义:
 * 假设keywords分为5个区间：(-INF, 3), [3, 5), [5, 8), [8, 10), [10, INF)
 * 5个区间分别对应：children[0]...children[4]
 *
 * m值是事先计算好的，计算的依据是让所有信息的大小正好等于页的大小
 * PAGE_SIZE = (m-1) * 4[keywords大小] + m*8[children大小]
 */
public class BPlusTreeNode {

    // 5叉树
    public static int m = 5;

    // 健值，用来划分数据区间
    public int[] keywords = new int[m - 1];

    // 保存子节点指针
    public BPlusTreeNode[] children = new BPlusTreeNode[m];

}
