package com.moheqionglin.alth.sort.insertSort;

import com.moheqionglin.alth.Util;

/**
 * @author wanli.zhou
 * @description
 * @time 12/01/2019 11:06 PM
 */
public class SimpleInsertSortAndShellSort {
    public static void main(String[] args) {
        int array[] = Util.mockArray(10);
        Util.print(array);

        simpleInsertSort(array);
        Util.print(array);

        shellSort1(array);
        Util.print(array);
    }

    private static void simpleInsertSort(int[] array) {
        for(int i = 1; i < array.length; i ++){//选元素
            for(int j = i - 1; j >= 0 && array[j] > array[j + 1]; j --){
                Util.swap(array, j, j + 1);
            }
        }
    }

    //https://www.cnblogs.com/youzhibing/p/4889037.html
    private static void shellSort1(int array[]){
        for( int gap = array.length / 2; gap >=1; gap /= 2){

            for(int i = gap; i < array.length; i ++){
                for(int j = i - gap; j >= 0 && array[j] > array[j+gap]; j -= gap ){
                    Util.swap(array, j, j + gap);
                }
            }

        }
    }



}