package com.moheqionglin.alth.sort.mergeSort;

import com.moheqionglin.alth.Util;

import java.util.Arrays;

/**
 * @author wanli.zhou
 * @description
 * @time 13/01/2019 10:16 PM
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] array = Util.mockArray(10);
        Util.print(array);

        System.out.println();
        System.out.println();
        
        mergeSort(array, 0, 9);
        Util.print(array);
    }

    private static int[] mergeSort(int[] array, int start, int end) {
        if(array.length < 2){
            return array;
        }

        int mid = (start + end) / 2;
        if(start < end){
            mergeSort(array, start, mid);
            mergeSort(array, mid + 1, end);
            merge(array, start, mid, end);
        }
        return array;
    }

    private static void merge(int[] array, int start, int mid, int end) {
        int[] result = new int[end - start + 1];
        int k = 0;
        int i = start, j = mid + 1;
        for ( ; i < mid && j < end; ){
            if(array[i] <= array[j]){
                result [k] = array[i];
                i++;
            }else{
                result [k] = array[j];
                j++;
            }
            k++;
        }

        while (i <= mid){
            result[k++] = array[i++];
        }
        while (j <= end){
            result[k++] = array[j++];
        }

        System.arraycopy(result, 0, array, start, result.length);
    }

}