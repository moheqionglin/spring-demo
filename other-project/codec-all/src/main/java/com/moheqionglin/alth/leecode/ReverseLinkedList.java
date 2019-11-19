package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-14 14:37
 */
public class ReverseLinkedList {

    public static void main(String[] args) {

    }

    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;

        while(cur != null){
            ListNode tmpCur = cur.next;

            cur.next = pre;
            pre = cur;
            cur = tmpCur;
        }
        return pre;
    }
}