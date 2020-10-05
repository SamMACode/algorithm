package org.interview.algorithm.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Sam Ma
 * @date 2020/10/05
 * 使用AC自动机实现多模式字符串匹配的算法
 */
public class AcAutoMata {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcAutoMata.class);

    private AcNode root = new AcNode('/');

    /**
     * 将模式串插入到AcAutoMata自动机中
     *
     * @param text
     */
    private void insertValue(char[] text) {
        AcNode p = this.root;
        for (int i = 0; i < text.length; i++) {
            // 由于输入数据中共有26个字符，计算char存储下标索引可通过(text[i]-'a')得到
            int index = text[i] - 'a';
            if (p.children[index] == null) {
                AcNode newNode = new AcNode(text[i]);
                p.children[index] = newNode;
            }
            // 因为字符串是存储在树结构的，每添加一个字符串中的字符 就需将p指针向下移动一个位置
            p = p.children[index];
        }
        // 插入的最后字符作为叶子结点（将其isEndingChar属性设置为true）
        p.isEndingChar = true;
        p.length = text.length;
    }

    /**
     * 用于构建失败指针pointer
     */
    public void buildFailurePointer() {
        Queue<AcNode> queue = new LinkedList<>();
        // root结点的失败指针指向它自己，也即为null值
        root.fail = null;
        queue.add(root);

        while (!queue.isEmpty()) {
            AcNode p = queue.remove();
            for (int i = 0; i < 26; ++i) {
                AcNode pc = p.children[i];
                // 对于trie树中为null的结点，则直接进行跳过处理
                if (null == pc) {
                    continue;
                }
                if (p == root) {
                    pc.fail = root;
                } else {
                    AcNode q = p.fail;
                    while (q != null) {
                        // 根据qc = q.children[pc.data - 'a']计算上一层p中是否存在匹配的前缀子串(qc != null)
                        AcNode qc = q.children[pc.data - 'a'];
                        if (qc != null) {
                            pc.fail = qc;
                            break;
                        }
                        // 当祖父层级不存在时，通过q = q.fail找祖父上一层级查看是否存在匹配的前缀子串
                        q = q.fail;
                    }
                    /*
                     * 若一直没有找到，则说明在trieTree树中不存在匹配的前缀子串，直接将pc.fail指向根结点
                     */
                    if (q == null) {
                        pc.fail = root;
                    }
                }
                queue.add(pc);
            }
        }
    }

    /**
     * 使用AcAutoMata依据text输入主串进行匹配
     *
     * @param text
     */
    public void match(char[] text) {
        int n = text.length;
        AcNode p = root;
        for (int i = 0; i < n; ++i) {
            int idx = text[i] - 'a';
            // 失败指针发挥作用的地方
            while (p.children[idx] == null && p != root) {
                p = p.fail;
            }
            /*
             * 若在trietree树中每次存在相应子节点，则每次都会将p进行下探(连续匹配的情况);
             * 如果没有匹配的，从将p指针从root结点开始重新进行匹配
             */
            p = p.children[idx];
            if (null == p) {
                p = root;
            }

            AcNode tmp = p;
            while (tmp != root) {
                // 打印出可以匹配的模式串(如果isEndingChar数值为true)
                if (tmp.isEndingChar == true) {
                    int pos = i - tmp.length + 1;
                    LOGGER.info("匹配起始下标:" + pos + "; 长度" + tmp.length);
                }
                tmp = tmp.fail;
            }
        }
    }

    public class AcNode {
        public char data;
        /**
         * 字符集中只包含a-z这26个字符
         */
        private AcNode[] children = new AcNode[26];
        /**
         * 结尾串为true
         */
        public boolean isEndingChar = false;
        /**
         * 当isEndingChar为true时，记录模式串的长度
         */
        public int length = -1;
        /**
         * 定义失败指针
         */
        public AcNode fail;

        public AcNode(char data) {
            this.data = data;
        }
    }

    /*public static void main(String[] args) {
        String[] patterns = {"c", "bc", "bcd", "abcd"};
        String text = "abcd";
        // 使用匹配模式用于构建AcAutoMata自动机, 然后使用模式子串构建失效指针
        AcAutoMata acAutoMata = new AcAutoMata();
        Arrays.stream(patterns).forEach(element -> acAutoMata.insertValue(element.toCharArray()));
        acAutoMata.buildFailurePointer();
        acAutoMata.match(text.toCharArray());
    }*/

}
