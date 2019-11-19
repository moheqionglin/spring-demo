package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-21 11:27
 */
public class LinkeddeleteDuplicates {
    public static void main(String[] args) {
        ListNode listNode = ListNode.generateList(new int[]{1, 1, 2, 2, 3, 3, 4, 4});
        ListNode.printLinkedList(new LinkeddeleteDuplicates().deleteDuplicates(listNode));

    }
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(-999999);
        ListNode dummyP = dummy;
        dummy.next = head;

        ListNode cur = head;
        int lastVal = dummy.val;
        while (cur != null){
            if(lastVal != cur.val){
                dummyP.next = cur;
                lastVal = cur.val;
                dummyP = cur;
            }

            cur = cur.next;
            dummyP.next = null;
        }
        return dummy.next;
    }
}