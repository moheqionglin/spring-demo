package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-21 14:46
 * 给定一个单链表 L：L0→L1→…→Ln-1→Ln ，
 * 将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→…
 *
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 *
 * 示例 1:
 *
 * 给定链表 1->2->3->4, 重新排列为 1->4->2->3.
 * 示例 2:
 *
 * 给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3.
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reorder-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LinkedreorderList {
    public static void main(String[] args) {
        ListNode listNode = ListNode.generateList(new int[]{1, 2, 3, 4,5});
        new LinkedreorderList().reorderList(listNode);
        ListNode.printLinkedList(listNode);
    }

    public void reorderList(ListNode head) {
        if(head == null){
            return;
        }
        //找中间点
        ListNode fast = head;
        ListNode slow = head;
        while(fast.next != null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        fast = slow.next;
        slow.next = null;

        ListNode list2 = reverseList(fast);
        slow = list2;
        for(ListNode tmp = head; tmp != null; ){
            ListNode t = tmp.next;
            if(slow != null){

                ListNode t1 = slow.next;
                tmp.next = slow;
                slow.next = t;


                slow = t1;
            }else{
                tmp.next = null;
            }
            tmp = t;

        }


    }

    public ListNode reverseList(ListNode head){
        ListNode dummy = new ListNode(-1009);

        for(ListNode tmp = head; tmp != null;){
            ListNode t = tmp.next;
            tmp.next = dummy.next;
            dummy.next = tmp;

            tmp = t;
        }
        return dummy.next;
    }
}