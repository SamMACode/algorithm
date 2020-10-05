package org.interview.algorithm.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sam Ma
 * @date 2020/10/04
 * 使用KMP算法实现字符串匹配功能(从好前缀中计算匹配的最长后缀子串，作为其移动的距离)
 */
public class KMPString {

    private static final Logger LOGGER = LoggerFactory.getLogger(KMPString.class);

    /**
     * a和b分别表示主串和模式串，n和m分别表示主串和模式串的长度
     *
     * @param mainChars
     * @param length
     * @param pattern
     * @param plength
     * @return
     */
    public static int kmp(char[] mainChars, int length, char[] pattern, int plength) {
        int[] next = getNexts(pattern, plength);
        // j用于记录好前缀字串的长度，依据[j-1]从next数组中获取 最长可匹配前缀字串结尾字符下标
        int j = 0;
        for (int i = 0; i < length; ++i) {
            // 一直找到mainChars[i]和pattern[j]位置, 此时j为坏字符位置
            while (j > 0 && mainChars[i] != pattern[j]) {
                j = next[j - 1] + 1;
            }

            if (mainChars[i] == pattern[j]) {
                ++j;
            }
            // 当找到匹配模式串时，依据最大匹配子串将模式串向后进行滑动
            if (j == plength) {
                return i - plength + 1;
            }
        }
        return -1;
    }

    /**
     * 求解nexts函数，pattern表示模式串 plength为模式串的长度
     *
     * @param pattern
     * @param plength
     * @return
     */
    private static int[] getNexts(char[] pattern, int plength) {
        int[] next = new int[plength];
        // next[0]表示不存在前缀字串字串，给予其next数组默认值-1. 使用{ababacd}跑数，观察其结果
        next[0] = -1;
        int k = -1;
        for (int i = 1; i < plength; ++i) {
            /*
             * 当模式串的子串长度后移动一位，那么就要判断之前最长可匹配前缀子串的下标加一对应的字符和最后一位字符是否相等：
             * 1) 如果相等就是如下的逻辑，k+1就是模式串子串的最长可匹配前缀子串的下标；
             * 2) 若不相等，就判断后移一位之前最长可匹配前缀子串的下标加一是否和最后一位字符相等，直到k=-1. 如果相等，就是相等的逻辑;
             */
            while (k != -1 && pattern[k + 1] != pattern[i]) {
                k = next[k];
            }
            if (pattern[k + 1] == pattern[i]) {
                ++k;
            }
            next[i] = k;    // next数组中存储的数值[-1, -1, 0, 1, 2, -1]
        }
        return next;
    }

    /*public static void main(String[] args) {
        String matcher = "university";
        String sentence = "CMUisaglobalresearchuniversityknownforitsworld-class";
        // 使用BoyerMoore进行字符串匹配，返回匹配字符串的下标索引
        Integer matchedIndex = KMPString.kmp(sentence.toCharArray(), sentence.length(), matcher.toCharArray(),
                matcher.length());
        // 0  [main] INFO  org.interview.algorithm.string.KMPString  - matched index value is: [20]
        LOGGER.info("matched index value is: [{}]", matchedIndex);
    }*/

}
