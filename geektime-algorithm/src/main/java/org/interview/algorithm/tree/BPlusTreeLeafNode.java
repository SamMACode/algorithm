package org.interview.algorithm.tree;

/**
 * @author Sam Ma
 * @date 2021/03/07
 * B+树中叶子节点的定义:
 * B+树中的叶子节点跟内部节点是不一样的，叶子节点存储的是值，而非区间，
 * 这个定义里，每个叶子节点存储3个数据行的键值及地址信息
 *
 * k值是事先计算得到的，计算的依据是让所有信息的大小正好等于页的大小：
 * PAGE_SIZE = k+4[keyw..大小] + k*8[dataAd..大小] + 8[prev大小] + 8[next大小]
 */
public class BPlusTreeLeafNode {

    public static int k = 3;

    // 数据的键值
    public int[] keywords = new int[k];

    // 数据的地址
    public long[] dataAddress = new long[k];

    // 这个结点在链表中的前驱结点
    public BPlusTreeLeafNode prev;

    // 这个结点在链表中的后继结点
    public BPlusTreeLeafNode next;

}
