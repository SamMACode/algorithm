package org.interview.algorithm.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sam Ma
 * @date 2020/11/28
 * leetcode 求3数之和为0的元组，https://leetcode-cn.com/problems/3sum/，先对数组排序，然后使用两个pointer双向检测
 */
class ThreeSum {
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> tuples = new ArrayList<>();
        // 三数之和为0，要求nums中至少需要存在3个元素
        if (null == nums || nums.length <= 2) {
            return tuples;
        }
        // 首先对数组进行排序，保证数组元素都是有序的 O(nlogn)
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) break;    // 若排序后的第一个元素大于0, 则不存在匹配的元组
            if (i > 0 && nums[i] == nums[i - 1]) continue;   // 去除掉重复的元素
            int target = -nums[i];
            // 开启双重指针进行检测（位置？），其中leftp指向第一个元素、rightp指向最后一个元素
            int leftp = i + 1, rightp = nums.length - 1;
            while (leftp < rightp) {
                if (nums[leftp] + nums[rightp] == target) {
                    tuples.add(Arrays.asList(nums[i], nums[leftp], nums[rightp]));
                    // 先进行leftp和rightp的变化，需要去除重复元素（需要与已经计算过的元素做判断）
                    leftp++;
                    rightp--;
                    while (leftp < rightp && nums[leftp] == nums[leftp - 1]) leftp++;
                    while (leftp < rightp && nums[rightp] == nums[rightp + 1]) rightp--;
                } else if (nums[leftp] + nums[rightp] < target) {
                    leftp++;
                } else {
                    rightp--;
                }
            }
        }
        return tuples;
    }

    /*public static void main(String[] args) {
        List<List<Integer>> tuples = ThreeSum.threeSum(new int[]{-2, 0, 1, 1, 2});
        System.out.println("tuples" + tuples.toString());
    }*/
}