package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-16 10:56
 */
public class Linkedpartition {
    public static void main(String[] args) {
        ListNode listNode = ListNode.generateList(new int[]{1, 4, 3, 2, 5, 2});
        ListNode partition = new Linkedpartition().partition(listNode, 3);
        ListNode.printLinkedList(partition);
    }
    public ListNode partition(ListNode head, int x) {

        ListNode p = head;

        ListNode dummySmall = new ListNode(-100);
        ListNode dummyBig = new ListNode(-100);

        ListNode dummySmallP = dummySmall;
        ListNode dummyBigP = dummyBig;

        while(p != null){
            if(p.val >= x) {
                dummyBigP.next = p;
                dummyBigP = dummyBigP.next;

            }else{
                dummySmallP.next = p;
                dummySmallP = dummySmallP.next;

            }
            p = p.next;
        }
        dummyBigP.next = null;
        dummySmallP.next = dummyBig.next;
        return  dummySmall.next;
    }

}