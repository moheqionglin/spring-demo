package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-15 19:22
 */
public class LinkedListSwapPairs {

    public static void main(String[] args) {
        ListNode listNode = ListNode.generateList(new int[]{1, 2, 3});
        ListNode.printLinkedList(new LinkedListSwapPairs().swapPairs(listNode));

    }

    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode pre = head;
        ListNode after = head.next;
        ListNode newList = null;

        ListNode cacheNode =  null;
        while(after != null){
            ListNode tmp = after.next;
            //反转
            after.next = pre;
            pre.next = null;

            if(cacheNode != null){
                cacheNode.next = after;
            }
            cacheNode = pre;

            if(newList == null){
                newList = after;
            }
            pre = tmp;

            if(pre == null || pre.next == null){
                cacheNode.next = pre;
                break;
            }else{
                after = pre.next;
            }
        }
        return     newList;
    }
}