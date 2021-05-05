package org.interview.algorithm.array;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sam Ma
 * @date 2021/05/05
 * 求众数，给定一个大小为n的数组，找到其中的多数元素-元素出现次数大于[n/2]的元素
 * leetcode https://leetcode-cn.com/problems/majority-element/
 */
public class MajorityElement {

    private static final Logger logger = LoggerFactory.getLogger(MajorityElement.class);

    /**
     * 使用HashMap来统计元素出现的次数 (通过Map找到频率最高的元素)
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> countsMap = countNums(nums);

        Map.Entry<Integer, Integer> majorityEntry = null;
        for (Map.Entry<Integer, Integer> entry : countsMap.entrySet()) {
            if (majorityEntry == null || entry.getValue() > majorityEntry.getValue()) {
                majorityEntry = entry;
            }
        }
        return majorityEntry.getKey();
    }

    /**
     * 使用HashMap统计元素出现次数
     *
     * @param nums
     * @return
     */
    private Map<Integer, Integer> countNums(int[] nums) {
        Map<Integer, Integer> countsMap = new HashMap<>();
        for (int num : nums) {
            if (!countsMap.containsKey(num)) {
                countsMap.put(num, 1);
            } else {
                countsMap.put(num, countsMap.get(num) + 1);
            }
        }
        return countsMap;
    }

    /**
     * another solution: 将数组中元素按单调性排序，下标在[n/2]的元素一定是众数
     *
     * @param nums
     * @return
     */
    public int majorityElem(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /*public static void main(String[] args) {
        int[] dataArray = new int[]{2, 4, 5, 5, 6, 5, 5};
        MajorityElement majority = new MajorityElement();
        int element = majority.majorityElement(dataArray);
        logger.info("major element which occurs num > array.length/2 is: {} ", element);
        // 使用sort排序的方式，直接取[n/2]处的元素 majorityElem(int[] nums)
        int sortelem = majority.majorityElem(dataArray);
        logger.info("use sort major element which occurs num > array.length/2 is: {} ", sortelem);
    }*/

}
