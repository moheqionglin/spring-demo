package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-14 22:55
 * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
 *
 * 示例：
 *
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-two-sorted-lists
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LinkedMergeTwoSortedLists {
    public static void main(String[] args) {
        ListNode list1 = ListNode.generateList(new int[]{1,2,4});
        ListNode list2 = ListNode.generateList(new int[]{1,3,4});

        ListNode.printLinkedList(new LinkedMergeTwoSortedLists().mergeTwoLists(list1, list2));

        ListNode list11 = null;
        ListNode list22 = ListNode.generateList(new int[]{1});

        System.out.println();
        ListNode.printLinkedList(new LinkedMergeTwoSortedLists().mergeTwoLists(list11, list22));
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null){
            return l2;
        }
        if (l2 == null){
            return l1;
        }

        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;

        ListNode l1Cur = l1;
        ListNode l2Cur = l2;


        while (l1Cur != null && l2Cur != null){
            int val ;
            if(l1Cur.val < l2Cur.val){
                val = l1Cur.val;
                l1Cur = l1Cur.next;
            }else{
                val = l2Cur.val;
                l2Cur = l2Cur.next;
            }
            cur.next = new ListNode(val);
            cur = cur.next;
        }

        if (l1Cur != null){
            cur.next = l1Cur;
        }
        if (l2Cur != null){
            cur.next = l2Cur;
        }
        return dummy.next;
    }
}