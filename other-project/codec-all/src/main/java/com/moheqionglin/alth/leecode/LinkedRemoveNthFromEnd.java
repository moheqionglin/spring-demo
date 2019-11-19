package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-14 22:42
 *
 * 删除链表的倒数第N个节点
 *
 * 输入:
 * [1,2,3,4,5]
 * 2
 * 输出
 * [5]
 * 预期结果
 * [1,2,3,5]
 */
public class LinkedRemoveNthFromEnd {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode n = new LinkedRemoveNthFromEnd().removeNthFromEnd(head, 2);
        ListNode.printLinkedList(n);
    }
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode preFirst = null;
        ListNode first = head;
        ListNode second = head;
        for(int i = 0 ; i < n; i ++){
            second = second.next;
        }

        while(second != null){
            preFirst = first;
            first = first.next;
            second = second.next;
        }
        if(preFirst == null){
            head = head.next;
        }else{
            preFirst.next = first.next;
        }

        return head;
    }
}