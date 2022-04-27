package com.moheqionglin.alth.leecode;

public class LinkedReverse {
    public static void main(String[] args) {
        ListNode listNode = ListNode.generateList(new int[]{1,2,3,4,5,6});
        ListNode.printLinkedList(listNode);
        System.out.println();
//        ListNode.printLinkedList(new LinkedreverseKGroup().reverseKGroup(listNode, 2));
        LinkedReverse linkedReverse = new LinkedReverse();
        linkedReverse.visit(listNode);
        System.out.println();
        linkedReverse.reverse(listNode);
        ListNode.printLinkedList(listNode);
    }

    public void visit(ListNode header){
        if(header.next != null){
            visit(header.next);
            System.out.print(header.val);
        }
    }


    public void reverse(ListNode header){
        if(header.next.next != null){
            reverse(header.next);
        }else{
            header.next.next = header;
            header.next = null;
        }
    }
}
