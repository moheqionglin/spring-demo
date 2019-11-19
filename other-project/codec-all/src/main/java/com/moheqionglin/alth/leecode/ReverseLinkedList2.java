package com.moheqionglin.alth.leecode;
/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-14 14:56
 */
public class ReverseLinkedList2 {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode.printLinkedList(head);
        ListNode listNode = new ReverseLinkedList2().reverseBetween2(head, 2, 5);
        System.out.println();
        ListNode.printLinkedList(listNode);
    }


    public ListNode reverseBetween1(ListNode head, int m, int n) {
        ListNode pre = null;
        ListNode cur = head;
        ListNode mPreNode = null;
        ListNode revertLastNode = null;
        int index = 1;
        while(cur != null){
            if(index < m){
                pre = cur;
                cur = cur.next;
            }else if (index == m){
                mPreNode = pre;
                revertLastNode = cur;
                pre = cur;
                cur = cur.next;
            }else if(index <= n){
                ListNode tmp = cur.next;
                cur.next = pre;
                pre = cur;
                cur = tmp;
            }else {
                break;
            }
            index ++;

        }
        if(m ==1){
            head = pre;
        }else{
            mPreNode.next = pre;
        }
        revertLastNode.next = cur;
        return head;
    }

    public ListNode reverseBetween2(ListNode head, int m, int n) {
        ListNode cur = head;
        int index = 1;
        ListNode stdNode = null;
        while (cur != null){
            if(index < m){
                cur = cur.next;

            }else if(index == m){
                stdNode = cur;
            }else if(index <= n){


                ListNode tmp = stdNode.next;
                stdNode.next = cur;
                cur.next = tmp;
            }else {
                break;
            }
            index ++;
        }
        return head;
    }
}