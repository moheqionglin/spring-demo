package com.moheqionglin.alth.sort.insertSort;

import com.moheqionglin.alth.Util;

/**
 * @author wanli.zhou
 * @description
 * @time 12/01/2019 11:06 PM
 */
public class SimpleInsertSort {
    public static void main(String[] args) {
        int array[] = Util.mockArray(10);
        Util.print(array);

        simpleInsertSort(array);
        Util.print(array);
    }

    private static void simpleInsertSort(int[] array) {
        for(int i = 1; i < array.length; i ++){//选元素
            dancheng(array, i);
        }
    }

    private static void dancheng(int[] array, int selectIndex) {
        int selectValue = array[selectIndex];
        int k = selectIndex - 1;
        for(; k >= 0 && array[k] > selectValue; k --){
            array[k + 1] = array[k];
        }
        k++;
        array[k] = selectValue;
    }

}