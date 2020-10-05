package org.interview.algorithm.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Sam Ma
 * @date 2020/10/05
 * 使用Trie树用于实现搜索引擎的搜索关键词提示功能(使用树结构，共用公共前缀子串)
 */
public class TrieTree {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrieTree.class);

    /**
     * 根结点存储'/'字符
     */
    private TrieNode root = new TrieNode('/');

    /**
     * 向Trie树中插入一个字符串
     *
     * @param text
     */
    public void insertValue(char[] text) {
        TrieNode p = root;
        for (int i = 0; i < text.length; i++) {
            // 由于输入数据中共有26个字符，计算char存储下标索引可通过(text[i]-'a')得到
            int index = text[i] - 'a';
            if (p.children[index] == null) {
                TrieNode newNode = new TrieNode(text[i]);
                p.children[index] = newNode;
            }
            // 因为字符串是存储在树结构的，每添加一个字符串中的字符 就需将p指针向下移动一个位置
            p = p.children[index];
        }
        // 插入的最后字符作为叶子结点（将其isEndingChar属性设置为true）
        p.isEndingChar = true;
    }

    /**
     * 在Trie树中查找匹配的字符串信息
     *
     * @param pattern
     * @return
     */
    public boolean findValue(char[] pattern) {
        TrieNode p = root;
        // 从根结点开始逐个字符串进行匹配，直到trie树中不存在指定子结点或pattern匹配结束
        for (int i = 0; i < pattern.length; i++) {
            int index = pattern[i] - 'a';
            if (p.children[index] == null) {
                return false;
            }
            p = p.children[index];
        }
        if (p.isEndingChar == false) {
            return false;
        } else {
            return true;
        }
    }

    public class TrieNode {
        public char data;
        /**
         * 使用a-z共26个字母作为trie树的存储结构(多叉树结构，使用二叉树leftPointer和rightPointer不合适)
         */
        public TrieNode[] children = new TrieNode[26];
        /**
         * 是否为Trie树的结束子串(叶子节点)
         */
        public boolean isEndingChar = false;

        public TrieNode(char data) {
            this.data = data;
        }
    }

    /*public static void main(String[] args) {
        String[] values = new String[]{"how", "hi", "her", "hello", "so", "see"};
        // 将这些数据插入到TrieTree中，然后依据执行的字符串查看其树否存在
        TrieTree trieTree = new TrieTree();
        Arrays.stream(values).forEach(element -> trieTree.insertValue(element.toCharArray()));
        LOGGER.info("whether hello is exist in trieTree: [{}]", trieTree.findValue("hello".toCharArray()));
    }*/

}
