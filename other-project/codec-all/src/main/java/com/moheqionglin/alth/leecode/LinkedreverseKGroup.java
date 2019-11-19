package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-21 10:41
 */
public class LinkedreverseKGroup {

    public static void main(String[] args) {
        ListNode listNode = ListNode.generateList(new int[]{1,2,3,4,5,6});
        ListNode.printLinkedList(new LinkedreverseKGroup().reverseKGroup(listNode, 2));
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(-100);
        ListNode dummyCur = dummy;

        ListNode cur = head;
        ListNode tmpHead = head;
        int i = 0;
        while (cur != null){
            i++ ;
            ListNode tmpCurNextNode = cur.next;
            if(i == k){
                cur.next = null;
                dummyCur.next = reverseList(tmpHead);
                dummyCur = tmpHead;
                tmpHead = tmpCurNextNode;
                i = 0;

            }
            cur = tmpCurNextNode;
        }
        if(tmpHead != null){
            dummyCur.next = tmpHead;
        }
        return dummy.next;
    }

    private ListNode reverseList(ListNode tmpHead) {
        ListNode dummy = new ListNode(-100);
        ListNode dummycur = dummy;
        ListNode cur = tmpHead;
        while (cur != null){
            ListNode tmp = cur.next;
            cur.next = dummycur.next;
            dummycur.next = cur;
            cur = tmp;
        }
        return dummy.next;
    }
}