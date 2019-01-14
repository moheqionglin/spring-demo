package com.moheqionglin.alth.sort.selectSort;

import com.moheqionglin.alth.Util;

/**
 * @author wanli.zhou
 * @description
 * @time 12/01/2019 9:27 PM
 */
public class SimpleSelectSort {
    public static void main(String[] args) {
        int[] array = Util.mockArray(10);

        Util.print(array);
        simpleSelectSort(array);
        Util.print(array);
    }


    private static void simpleSelectSort(int[] array) {
        for(int j = 0; j < array.length; j ++){
            dancheng(array, j);
        }

    }

    private static void dancheng(int[] array, int start) {
        //单程 找最大的 index， 然后放到 坐左边
        int maxIdx = start;
        for(int i = start ; i < array.length; i++ ){
            if(array[i] > array[maxIdx]){
                maxIdx = i;
            }
        }

        //jiaohuan
        int tmp = array[start];
        array[start] = array[maxIdx];
        array[maxIdx] = tmp;
    }
}