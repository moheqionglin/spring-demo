package com.moheqionglin.alth.leecode;

import org.apache.commons.lang3.StringUtils;
import org.apache.directory.shared.kerberos.codec.apRep.actions.ApRepInit;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 *
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 * 示例 4:
 *
 * 输入: s = ""
 * 输出: 0
 *
 */
public class FlowWindow {

    public static void main(String[] args) {
        String s = "abceaabceadgddbcbb";
        FlowWindow flowWindow = new FlowWindow();
        System.out.println(flowWindow.maxLength(s));
    }

    private int maxLength(String s) {
        if (StringUtils.isBlank(s)){
            return 0;
        }

        Set<Character> window = new HashSet<>();
        int left = 0, right = 0;
        int length = 0; String maxLengthSubStr = "";
        int length1 = s.length();
        while (right < length1){
            if(window.contains(s.charAt(right)) && left <= right){
                length = Math.max(length, right - left);
                maxLengthSubStr = length == (right - left) ? s.substring(left,right) : maxLengthSubStr;
                window.remove(s.charAt(left));
                left ++ ;
            }else{
                window.add(s.charAt(right));
                right ++ ;
            }
        }
        length = Math.max(length, right - left);
        maxLengthSubStr = length == (right - left) ? s.substring(left,right) : maxLengthSubStr;
        System.out.println(maxLengthSubStr);
        return length;
    }


}
