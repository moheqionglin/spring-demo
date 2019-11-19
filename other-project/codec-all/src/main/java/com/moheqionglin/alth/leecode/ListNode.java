package com.moheqionglin.alth.leecode;

public class ListNode {
    int val;
    ListNode next;

    public static ListNode generateList(int[] list) {
        ListNode head = new ListNode(list[0]);
        ListNode cur = head;
        for (int i = 1; i < list.length; i++) {
            cur.next = new ListNode(list[i]);
            cur = cur.next;
        }
        return head;
    }

    public static void printLinkedList(ListNode head) {
        ListNode cur = head;
        while (cur != null) {
            System.out.print(cur.val);
            if (cur.next != null) {
                System.out.print(" -> ");
            }
            cur = cur.next;
        }
    }

    ListNode(int x) {
        val = x;
    }

    ListNode() {

    }
}