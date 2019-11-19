package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-15 18:52
 */
public class LinkedListaddTwoNumbers {

    public static void main(String[] args) {
        ListNode listNode = ListNode.generateList(new int[]{1});

        ListNode listNode1 = ListNode.generateList(new int[]{9,9});

        ListNode.printLinkedList(new LinkedListaddTwoNumbers().addTwoNumbers(listNode, listNode1));
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1 == null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }
        ListNode l1Cur = l1;
        ListNode l2Cur = l2;
        ListNode head = null;
        ListNode cur = head;

        boolean jinwei = false;
        while(l1Cur != null && l2Cur != null){

            int val = l1Cur.val + l2Cur.val + (jinwei ? 1 : 0);
            if(val >= 10){
                jinwei = true;
                val = val - 10;
            }else{
                jinwei = false;
            }

            if(head == null){
                head = new ListNode(val);
                cur = head;
            }else{
                cur.next = new ListNode(val);
                cur = cur.next;
            }

            l1Cur = l1Cur.next;
            l2Cur = l2Cur.next;
        }

        while(l1Cur != null){
            int val = l1Cur.val  + (jinwei ? 1 : 0);
            if(val >= 10){
                jinwei = true;
                val = val - 10;
            }else{
                jinwei = false;
            }

            cur.next = new ListNode(val);
            l1Cur = l1Cur.next;
            cur = cur.next;
        }

        while(l2Cur != null){
            int val =  l2Cur.val + (jinwei ? 1 : 0);
            if(val >= 10){
                jinwei = true;
                val = val - 10;
            }else{
                jinwei = false;
            }

            cur.next = new ListNode(val);
            l2Cur = l2Cur.next;
            cur = cur.next;
        }
        if(jinwei){
            cur.next = new ListNode(1);
        }
        return head;
    }
}