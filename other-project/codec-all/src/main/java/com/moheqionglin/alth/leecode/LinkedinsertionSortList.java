package com.moheqionglin.alth.leecode;

import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-21 15:28
 */
public class LinkedinsertionSortList {
    public static void main(String[] args) {
        ListNode listNode = ListNode.generateList(new int[]{6, 5, 3, 1, 8, 7, 2, 4});
        ListNode listNode1 = new LinkedinsertionSortList().insertionSortList(listNode);
        ListNode.printLinkedList(listNode1);
    }

    public ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode(-435);

        for(ListNode tmp = head; tmp != null; ){
            ListNode tmp1 = dummy;
            for(; tmp1.next != null && tmp.val >  tmp1.next.val; tmp1 = tmp1.next){
            }
            ListNode list3 = tmp1.next;

            tmp1.next = tmp;
            ListNode tmp2 = tmp.next;
            tmp.next = list3;
            tmp = tmp2;

        }
        return dummy.next;
    }

}