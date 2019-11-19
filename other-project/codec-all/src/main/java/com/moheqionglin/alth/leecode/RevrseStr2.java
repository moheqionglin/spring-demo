package com.moheqionglin.alth.leecode;

import java.util.Stack;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-14 15:40
 *
 * 给定一个字符串和一个整数 k，你需要对从字符串开头算起的每个 2k 个字符的前k个字符进行反转。如果剩余少于 k 个字符，则将剩余的所有全部反转。如果有小于 2k 但大于或等于 k 个字符，则反转前 k 个字符，并将剩余的字符保持原样。
 *
 * 示例:
 *
 * 输入: s = "abcdefg", k = 2
 * 输出: "bacdfeg"
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-string-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 *
 */
public class RevrseStr2 {

    public static void main(String[] args) {
        String s = "1234567890";
        System.out.println(s);

        System.out.println(new RevrseStr2().reverseStr(s, 2));
    }
    // reverse(0, k) + (k, 2k) + reverse(zk +1, 3k) + (3k, 4k)
    public String reverseStr(String s, int k) {
        char as[] = s.toCharArray();
        for(int i = 0 ; i < as.length; i += 2*k){
            reverse(as, i, Math.min(i + k - 1, as.length - 1));
        }
        return new String(as);
    }

    public void reverse(char[] as, int left, int right){
        while (left < right){
            swap(as, right--, left++);
        }
    }

    public void swap(char[] as, int i , int j){
        char tmp = as[i];
        as[i] = as[j];
        as[j] = tmp;
    }
}