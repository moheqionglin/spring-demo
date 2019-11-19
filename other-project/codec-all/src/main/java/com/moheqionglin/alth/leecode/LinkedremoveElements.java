package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-21 16:29
 * 删除链表中等于给定值 val 的所有节点。
 *
 * 示例:
 *
 * 输入: 1->2->6->3->4->5->6, val = 6
 * 输出: 1->2->3->4->5
 */
public class LinkedremoveElements {
    public static void main(String[] args) {
        ListNode listNode = ListNode.generateList(new int[]{1,1});
        ListNode.printLinkedList(new LinkedremoveElements().removeElements(listNode, 1));

        ListNode listNode1 = ListNode.generateList(new int[]{1, 2, 6, 3, 4, 5, 6});
        ListNode.printLinkedList(new LinkedremoveElements().removeElements(listNode1, 6));
    }
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(-10034);
        ListNode cur = dummy;
        for(ListNode tmp = head; tmp != null; tmp = tmp.next){
            if(tmp.val != val){
                cur.next = tmp;
                cur = cur.next;
            }
        }
        cur.next = null;
        return dummy.next;
    }
}