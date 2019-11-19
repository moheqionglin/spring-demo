package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-22 19:55
 */
public class ReverseLinkedListWithRecurve {

    public static void main(String[] args) {
        ListNode listNode = ListNode.generateList(new int[]{1, 2, 3, 4, 5});
        ListNode.printLinkedList(new ReverseLinkedListWithRecurve().reverseList(listNode));
    }
    public ListNode reverseList(ListNode head) {

        if(head == null || head.next == null){
            return head;
        }
        ListNode ln = reverseList(head.next);
        head.next.next = head;
        head.next = null;

        return ln;
    }
}