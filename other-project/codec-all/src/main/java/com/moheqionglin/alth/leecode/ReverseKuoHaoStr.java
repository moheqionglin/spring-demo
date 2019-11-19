package com.moheqionglin.alth.leecode;

import org.locationtech.jts.util.Assert;

import java.util.Stack;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-14 22:21
 *
 * 给出一个字符串 s（仅含有小写英文字母和括号）。
 *
 * 请你按照从括号内到外的顺序，逐层反转每对匹配括号中的字符串，并返回最终的结果。
 *
 * 注意，您的结果中 不应 包含任何括号。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：s = "(abcd)"
 * 输出："dcba"
 * 示例 2：
 *
 * 输入：s = "(u(love)i)"
 * 输出："iloveu"
 * 示例 3：
 *
 * 输入：s = "(ed(et(oc))el)"
 * 输出："leetcode"
 * 示例 4：
 *
 * 输入：s = "a(bcdefghijkl(mno)p)q"
 * 输出："apmnolkjihgfedcbq"
 *  
 *
 * 提示：
 *
 * 0 <= s.length <= 2000
 * s 中只有小写英文字母和括号
 * 我们确保所有括号都是成对出现的
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-substrings-between-each-pair-of-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */


public class ReverseKuoHaoStr {

    public static void main(String[] args) {
        ReverseKuoHaoStr reverseKuoHaoStr = new ReverseKuoHaoStr();
        String s1 = "a(bcdefghijkl(mno)p)q";
        String s1Res = "apmnolkjihgfedcbq";
        Assert.equals(s1Res, reverseKuoHaoStr.reverseParentheses(s1));

        String s2 = "(ed(et(oc))el)";
        String s2Res = "leetcode";
        Assert.equals(s2Res, reverseKuoHaoStr.reverseParentheses(s2));

        String s3 = "(u(love)i)";
        String s3Res=  "iloveu";
        Assert.equals(s3Res, reverseKuoHaoStr.reverseParentheses(s3));
    }

    public String reverseParentheses(String s) {
        Stack<Integer> kuohaoStack = new Stack<Integer>();
        char[] as = s.toCharArray();
        for(int i = 0 ; i < as.length ; i ++){
            if(as[i] == '('){
                kuohaoStack.push(i);
            }else if (as[i] == ')'){
                Integer kuoHao = kuohaoStack.pop();
                reverse(as, kuoHao + 1, i - 1);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < as.length; i++){
            if (as[i] != ')' && as[i] != '('){
                sb.append(as[i]);
            }

        }

        return sb.toString();
    }

    public void reverse(char[] as, int start, int end){
        for(int i = start,  j = end;i < j ;){
            swap(as, i++, j--);
        }
    }

    public void swap(char[] as, int i , int j){
        char tmp = as[i];
        as[i] = as[j];
        as[j] = tmp;
    }
}