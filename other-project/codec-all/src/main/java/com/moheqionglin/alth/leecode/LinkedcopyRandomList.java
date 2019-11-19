package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-21 14:26
 */

public class LinkedcopyRandomList {

    public static void main(String[] args) {
        Node node1 = new Node(-1, null, null);
        Node node2 = new Node(8, null, null);
        Node node3 = new Node(7, null, null);
        Node node4 = new Node(-3, null, null);
        Node node5 = new Node(4, null, null);

        node1.next = node2;
        node1.random = node5;
        node2.next = node3;
        node2.random = node4;
        node3.next = node4;
        node3.random = null;
        node4.next = node5;
        node4.random = null;
        node5.random = node1;

        for(Node tmp = node1; tmp != null; tmp = tmp.next){
            System.out.print(tmp.hashCode() + "= " + tmp.val + ", ");
            System.out.print((tmp.random == null ? null : tmp.random.hashCode() )+ "= "+ (tmp.random == null ? null : tmp.random.val)+"\t");
        }
        System.out.println("====");
        Node node = new LinkedcopyRandomList().copyRandomList(node1);
        for(Node tmp = node; tmp != null; tmp = tmp.next){
            System.out.print(tmp.hashCode() + "= " + tmp.val + ", ");
            System.out.print((tmp.random == null ? null : tmp.random.hashCode() )+ "= "+ (tmp.random == null ? null : tmp.random.val)+"\t");
        }
    }


    public Node copyRandomList(Node head) {

        Node cur = head;
        while(cur != null){
            Node newNode = new Node(cur.val, cur.next, null);
            cur.next = newNode;
            cur = newNode.next;
        }
        //修复复制出来的 random
        cur = head;
        while(cur != null){
            cur.next.random = cur.random == null ? null : cur.random.next;
            cur = cur.next.next;
        }

        Node dummy = new Node();
        Node p = dummy;
        cur = head;
        while(cur != null){
            p.next = cur.next;
            p = p.next;

            cur.next = cur.next.next;
            cur = cur.next;
        }
        return dummy.next;
    }
}
class Node {
    public int val;
    public Node next;
    public Node random;

    public Node() {}

    public Node(int _val,Node _next,Node _random) {
        val = _val;
        next = _next;
        random = _random;
    }
}