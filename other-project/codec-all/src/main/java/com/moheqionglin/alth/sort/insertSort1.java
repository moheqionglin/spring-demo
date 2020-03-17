package com.moheqionglin.alth.sort;

import com.moheqionglin.alth.Util;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-09 18:45
 *
 */
public class insertSort1 {

    public static void main(String[] args) {
        int[] list = new int[]{4, 5, 1, 3, 9, 8, 6, 2, 7};
        select(list);
        Util.print(list);
    }

    private static void select(int[] list) {
        for(int gap = list.length / 2 ; gap > 0; gap /= 2){
            for(int i = gap ; i < list.length - gap; i++){
                for(int j = i + 1 ; j > 0; j --){
                    if(list[j] > list[j -1]){
                        Util.swap(list, j, j - 1);
                    }
                }
            }
        }
    }


}