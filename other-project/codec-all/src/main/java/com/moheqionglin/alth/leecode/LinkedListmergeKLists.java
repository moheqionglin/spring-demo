package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-16 11:12
 * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
 *
 * 示例:
 *
 * 输入:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * 输出: 1->1->2->3->4->4->5->6
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-k-sorted-lists
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LinkedListmergeKLists {

    public static void main(String[] args) {
        ListNode listNode = ListNode.generateList(new int[]{1, 4, 5});
        ListNode listNode2 = ListNode.generateList(new int[]{1, 3, 4});
        ListNode listNode3 = ListNode.generateList(new int[]{2, 6});

        ListNode listNode1 = new LinkedListmergeKLists().mergeKLists(new ListNode[]{listNode, listNode2, listNode3});
        ListNode.printLinkedList(listNode1);
    }
    //分支
    public ListNode mergeKLists(ListNode[] lists) {

        return mergeSort(lists, 0, lists.length - 1);
    }

    private ListNode mergeSort(ListNode[] lists, int left, int right) {
        if(left == right){
            return lists[left];
        }
        int mid = (left + right) >> 1;

        ListNode listNode = mergeSort(lists, left, mid);

        ListNode listNode1 = mergeSort(lists, mid + 1, right);

        return new LinkedMergeTwoSortedLists().mergeTwoLists(listNode, listNode1);

    }


}