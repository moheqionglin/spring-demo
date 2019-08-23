package com.moheqionglin.alth.other;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-03 10:59
 * 不重复子串长度
 */
public class MaxSubChar {
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcbcde"));
        System.out.println(lengthOfLongestSubstring2("abcbcde"));
        System.out.println(lengthOfLongestSubstring3("abcbcde"));
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

    //[i, j] 滑动窗口
    public static int lengthOfLongestSubstring2(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int i=0,j=0;
        int max = 0;
        while (i<s.length() && j < s.length()){
            if(!map.containsKey(s.charAt(j))){
                map.put(s.charAt(j), j);
                max = Math.max(max, j - i);
            }else{
                i = map.get(s.charAt(j));
                map.put(s.charAt(j), j);
            }
            j++;
        }
        return max;
    }
    public static int lengthOfLongestSubstring3(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }
}