package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-15 14:56
 *
 * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
 *
 * 示例 1:
 *
 * 输入: 1->2->3->4->5->NULL, k = 2
 * 输出: 4->5->1->2->3->NULL
 * 解释:
 * 向右旋转 1 步: 5->1->2->3->4->NULL
 * 向右旋转 2 步: 4->5->1->2->3->NULL
 * 示例 2:
 *
 * 输入: 0->1->2->NULL, k = 4
 * 输出: 2->0->1->NULL
 * 解释:
 * 向右旋转 1 步: 2->0->1->NULL
 * 向右旋转 2 步: 1->2->0->NULL
 * 向右旋转 3 步: 0->1->2->NULL
 * 向右旋转 4 步: 2->0->1->NULL
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/rotate-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LinkedRotateRight {
    public static void main(String[] args) {
        ListNode list1 = ListNode.generateList(new int[]{1, 2, 3});

        ListNode.printLinkedList(new LinkedRotateRight().rotateRight(list1, 2000000000));

    }
    public ListNode rotateRight(ListNode head, int k) {
        if(head == null || head.next == null || k == 0){
            return head;
        }
        ListNode cur = head;
        int nodeCnt = 0;
        while(cur.next != null){
            nodeCnt++;
            cur = cur.next;
        }
        nodeCnt++;
        ListNode tail = cur;


        //找到 nodeCnt - k%nodeCnt 的位置 切开

        cur = head;
        int i = 0;
        while(cur != null){
            i++;
            if(i == (nodeCnt - k%nodeCnt)){
                break;
            }
            cur = cur.next;

        }
        tail.next = head;
        head = cur.next;
        cur.next = null;

        return head;
    }
}