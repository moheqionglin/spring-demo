package com.moheqionglin.alth.sort.mergeSort;

import com.moheqionglin.alth.Util;

import java.util.Arrays;

/**
 * @author wanli.zhou
 * @description
 * @time 13/01/2019 10:16 PM
 *
 *
 * 归并类排序，
 *              [8 4 5 7 1 3 6 2]
 * 分          [8 4]  5 7]     [1 3]  [6 2]
 *           [8 4]   [5 7]    [1 3]   [6 2]
 *       [8]   [4]   [5]   [7]    [1]   [3]   [6]  2]
 *    ------------------------------------------------
 *       [4 8]   [5 7]    [1 3]   [2 6]
 * 治      [4 5 7 8]       [1 2 3 6]
 *          [1 2 3 4 5 6 7 8]
 *
 *  非常类似于 二叉树的遍历递归算法
 *
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] array = Util.mockArray(10);
        Util.print(array);

        System.out.println();
        System.out.println();
        
        mergeSort(array, 0, array.length - 1);
        Util.print(array);
    }

    private static void mergeSort(int[] array, int start, int end) {
        if(start >= end){
            return;
        }
        //后序遍历二叉树
        int mid = (start + end) / 2;
        //含mid
        mergeSort(array, start, mid);
        //不含mid
        mergeSort(array, mid + 1, end);

        zhi(array, start, mid, end);

    }

    /**
     *
     * @param array
     * @param start
     * @param mid
     * @param end
     *
     *  array[ start, mid]   array[mid + 1, end]
     *   宏观上 数组1 < 数组2
     *   微观调整，  主键取数组2 和数组1 比较
     *
     */
    private static void zhi(int[] array, int start, int mid, int end) {
        int tmp[] = new int[end - start + 1];

        int i = start, j = mid + 1, k = 0;

        for(; i<= mid && j <= end; ){
            if(array[i] < array[j]){
                tmp[k] = array[i];
                k++;
                i++;
            }else{
                tmp[k] = array[j];
                k ++;
                j++;
            }
        }
        for(; i <= mid; i++){
            tmp[k] = array[i];
            k++;
        }
        for(; j <= end; j++){
            tmp[k] = array[j];
            k++;
        }
//        Util.print(tmp);
        //把tmp中有序数列拷贝到 array中
       System.arraycopy(tmp, 0, array, start, tmp.length);

    }

}