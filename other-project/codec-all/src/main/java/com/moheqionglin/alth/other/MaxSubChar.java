package com.moheqionglin.alth.other;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-03 10:59
 */
public class MaxSubChar {
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcbcde"));
    }

    public static int lengthOfLongestSubstring(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j))){
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            }
            else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }

}