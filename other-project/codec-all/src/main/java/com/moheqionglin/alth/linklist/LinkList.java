package com.moheqionglin.alth.linklist;

/**
 * @author wanli.zhou
 * @description
 * @time 13/01/2019 7:40 PM
 */
public class LinkList {

    public static void main(String[] args) {

        //有环 链表  1 2 3 4 5 6 7 8 9 4
        Node circleList = mockList();

        //hasCircle();
        System.out.println("====是否有环====");
        System.out.println(hasCircle(circleList));


        //输出 环形的周长
        System.out.println("====环周长====");
        System.out.println(circleLength(circleList));

        //输出环形入口
        System.out.println("====环入口====");
        System.out.println(circleJoinPoint(circleList));
    }

    /**
     *
     * @param head
     * @return
     *
     * 思路：
     *       1-2-3-4-5--|
     *             |    6
     *             9    7
     *             |----8
     *
     *  假设 第一圈快慢指针相遇点在 距离入口的距离是 x， 其中从开头到环形入口距离是 L , 环形剩余距离是 y
     *
     *  第一次相遇时候：那么快指针走过的路程是：  L + 1 * (x + y) + x 也就是 快指针走了环形一周零x的距离
     *               慢指针走过的距离是： L + x
     *               因为快指针速度是满指针速度两倍， 所以相同时间内路程也是两倍
     *                L + 1 * (x + y) + x  = 2 （L+ x）
     *                => L + 2x + y = 2L + 2x
     *                => L = y
     *          也就是 第一次相遇地点 到环形入口距离 = head到环形入口距离
     *
     */
    private static Node circleJoinPoint(Node head) {
        if(!hasCircle(head)){
            return null;
        }

        Node fast, slow, firstMeetNode = null;
        fast = slow = head;

        while (fast.next != null && fast.next.next != null){

            fast = fast.next.next;
            slow = slow.next;

            if(fast == slow){
                firstMeetNode = fast;
                break;
            }
        }

        if(firstMeetNode == null){
            return null;
        }

        Node newPoint = head;
        while (newPoint != firstMeetNode){
            newPoint = newPoint.next;
            firstMeetNode = firstMeetNode.next;
        }
        return newPoint;
    }

    /**
     *
     * @param head
     * @return
     *
     * 思路：
     *    第一次相遇，开始到第二次相遇，证明快指针比曼指针 夺走了一圈。
     */
    private static int circleLength(Node head) {
        if(!hasCircle(head)){
            return 0;
        }

        Node fast, slow;
        fast = slow = head;

        int meetCount = 0;
        int circleLen = 0;
        while (fast.next != null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;

            if(fast == slow){
                meetCount++;
            }
            if(meetCount == 1){
                circleLen ++;
            }
            if(meetCount > 1){
                return circleLen;
            }
        }

        return 0;
    }

    private static boolean hasCircle(Node head) {
        if(head == null || head.next == null){
            return false;
        }

        Node fast, slow;
        fast = slow = head;

        while (fast.next != null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;

            if(fast == slow){
                return true;
            }
        }
        return false;
    }

    //有环 链表  1 2 3 4 5 6 7 8 9 4
    private static Node mockList() {
        Node head = new Node();

        head.next = new Node(1,
                new Node(2,
                        new Node(3,
                                new Node(4,
                                        new Node(5,
                                                new Node(6,
                                                        new Node(7, new Node(8, new Node(9, null)) )))))));


        Node node4 = head;
        while(node4 != null){
            node4 = node4.next;
            if(node4.value == 4){
                break;
            }
        }
        Node node9 = head;
        while(node9 != null){
            node9 = node9.next;
            if(node9.value == 9){
                break;
            }
        }

        node9.next = node4;

        return head;
    }

    private static class Node{
        public int value;
        public Node next;

        public Node() {
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", next=" + (next == null ? null : next.value) +
                    '}';
        }
    }

}