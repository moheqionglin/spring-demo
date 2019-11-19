package com.moheqionglin.alth.leecode;

import java.util.Stack;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-21 16:44
 */
public class LinkedisPalindrome {
    public static void main(String[] args) {
        ListNode listNode = ListNode.generateList(new int[]{1, 2, 3, 2, 1});
        System.out.println(new LinkedisPalindrome().isPalindrome(listNode));
    }
    public boolean isPalindrome(ListNode head) {
        Stack<Integer> stack = new Stack();

        for(ListNode tmp = head; tmp != null; tmp = tmp.next){
            if(!stack.isEmpty() && stack.peek() == tmp.val){
                stack.pop();
            }else {
                stack.push(tmp.val);
            }
        }
        return stack.size() <= 1;
    }
}