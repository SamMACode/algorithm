package org.interview.algorithm.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sam Ma
 * @date 2020/10/03
 * BoyerMoore字符串匹配函数实现（使用好后缀、坏字符匹配规则 计算模式串向后滑动的位置，用于进行快速匹配）
 */
public class BoyerMoore {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoyerMoore.class);

    private static final int SIZE = 256;

    /**
     * 将模式串中的每个字符及其下标都存储在散列表中，取字符的ascii码值作为下标(value表示字符在模式串中的位置)
     *
     * @param pattern     模式串
     * @param length      模式串的长度
     * @param bcHashTable
     */
    private void generateBC(char[] pattern, int length, int[] bcHashTable) {
        for (int i = 0; i < SIZE; i++) {
            bcHashTable[i] = -1;
        }
        for (int i = 0; i < length; ++i) {
            // 计算pattern[i]的ascii数值
            int ascii = (int) pattern[i];
            bcHashTable[ascii] = i;
        }
    }

    /**
     * BM 算法代码的大框架，暂时未考虑后缀规则 仅用坏字符规则
     *
     * @param mainChars
     * @param length
     * @param pattern
     * @param plength
     * @return
     */
    public int bm(char[] mainChars, int length, char[] pattern, int plength) {
        // 记录模式串中每个字符最后出现的位置，之后调用generateBC构建坏字符hash表
        int[] badHash = new int[SIZE];
        generateBC(pattern, plength, badHash);

        // suffix数组中存储模式串中跟好后缀{u}相匹配的字串{u*}的起始下标值
        int[] suffix = new int[length];
        // 用于记录模式串的后缀字串能够匹配模式串的前缀字串，后缀字串abc匹配模式串abcabc位置，其中prefix[3]=true(下表值为后缀串长度)
        boolean[] prefix = new boolean[length];
        generateGS(pattern, plength, suffix, prefix);

        // i表示主串与模式串对齐的第一个字符,其会根据好后缀和坏后缀计算得到向后滑动的位置
        int i = 0;
        while (i <= length - plength) {
            // 模式串从后向前进行匹配，坏字符对应模式串中的下标是badj
            int badj;
            for (badj = plength - 1; badj >= 0; --badj) {
                if (mainChars[i + badj] != pattern[badj]) break;
            }
            // 匹配成功，返回主串与模式串中第一个匹配字符的位置
            if (badj < 0) {
                return i;
            }

            // 计算坏后缀进行滑动的长度: (坏字符badj在模式串中下标-坏字符在模式串中最后的位置)
            int badx = badj - badHash[(int) mainChars[i + badj]];

            int goody = 0;
            // 如果有好后缀的话：字符串匹配是从length-1位置开始比较，每比较一次向前移动一个位置
            if (badj < length - 1) {
                // 计算好后缀向后进行滑动的位置
                goody = moveByGS(badj, plength, suffix, prefix);
            }
            i = i + Math.max(badx, goody);
        }
        return -1;
    }

    /**
     * badCharIndex表示坏字符对应的模式串中字符的下标，plength表示模式串的长度
     *
     * @param badCharIndex
     * @param plength
     * @param suffix
     * @param prefix
     * @return
     */
    private int moveByGS(int badCharIndex, int plength, int[] suffix, boolean[] prefix) {
        // 计算得到好后缀的长度 (模式串最后一个字符下标 - 坏字符下标)
        int k = plength - 1 - badCharIndex;
        // 若suffix[k]不为-1，则表示存在匹配的模式子串，则将匹配的模式串与主串中好子串对齐
        if (suffix[k] != -1) {
            return badCharIndex - suffix[k] + 1;
        }
        // 其中badCharIndex为坏字符位置，badCharIndex+1为好后缀开始下标、badCharIndex+2为后缀字串开始下标
        for (int r = badCharIndex + 2; r <= plength - 1; ++r) {
            // 若好后缀的后缀字串存在与模式串前缀字串相同(也即prefix[plength-r]==true)，则直接将模式串向后移动r位
            if (prefix[plength - r] == true) {
                return r;
            }
        }
        return plength;
    }

    /**
     * 提前预处理suffix和prefix数组的数值
     *
     * @param pattern 表示模式串
     * @param plength 模式串的长度
     * @param suffix
     * @param prefix
     */
    private void generateGS(char[] pattern, int plength, int[] suffix, boolean[] prefix) {
        for (int i = 0; i < plength; i++) {
            suffix[i] = -1;
            prefix[i] = false;
        }

        // 例如模式串内容为: cabcab 匹配字串: b ab cab
        for (int i = 0; i < plength - 1; ++i) {
            int j = i;
            // k表示公共后缀字串长度，while循环中判断与pattern[0, plength-1]求公共后缀子串(拿第一个字符与最后一个比较)
            int klength = 0;
            while (j >= 0 && pattern[j] == pattern[plength - 1 - klength]) {
                --j;
                ++klength;
                // j+1表示公共后缀字串在pattern[0, i]中的起始下标
                suffix[klength] = j + 1;
            }
            // 如果公共后缀字串也是模式串中的前缀字串
            if (j == -1) prefix[klength] = true;
        }
    }

    /*public static void main(String[] args) {
        BoyerMoore boyerMoore = new BoyerMoore();
        String matcher = "university";
        String sentence = "CMUisaglobalresearchuniversityknownforitsworld-class";
        // 使用BoyerMoore进行字符串匹配，返回匹配字符串的下标索引
        Integer matchedIndex = boyerMoore.bm(sentence.toCharArray(), sentence.length(), matcher.toCharArray(),
                matcher.length());
        // 0  [main] INFO  org.interview.algorithm.string.BoyerMoore  - matched index value is: [20]
        LOGGER.info("matched index value is: [{}]", matchedIndex);
    }*/

}
