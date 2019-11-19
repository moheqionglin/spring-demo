package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-16 10:37
 */
public class LinkedListDeleteDuplicates {
    public static void main(String[] args) {
        ListNode listNode = ListNode.generateList(new int[]{1, 2, 3, 3, 4, 4, 5});

        ListNode listNode1 = new LinkedListDeleteDuplicates().deleteDuplicates(listNode);
        ListNode.printLinkedList(listNode1);

    }
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode slow = dummy;
        ListNode fast = dummy.next;

        while(fast != null){
            if(fast.next == null || fast.val != fast.next.val){
                if(slow.next == fast){
                    slow = fast;
                }else{
                    slow.next = fast.next;
                }
            }
            fast = fast.next;
        }
        return dummy.next;
    }
}