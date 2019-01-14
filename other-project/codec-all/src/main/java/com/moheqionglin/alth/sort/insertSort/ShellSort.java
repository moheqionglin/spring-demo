package com.moheqionglin.alth.sort.insertSort;

import com.moheqionglin.alth.Util;

/**
 * @author wanli.zhou
 * @description
 * @time 12/01/2019 10:38 PM
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] array = new int[]{8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        Util.print(array);
        shellSort(array);
        Util.print(array);
        array = new int[]{8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        sort(array);
        Util.print(array);
    }

    public static void sort(int[] arr) {
        //增量gap，并逐步缩小增量
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //从第gap个元素，逐个对其所在组进行直接插入排序操作
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                while (j - gap >= 0 && arr[j] < arr[j - gap]) {
                    //插入排序采用交换法
                    swap(arr, j, j - gap);
                    j -= gap;
                }
            }
        }
    }

    public static void swap(int[] arr, int a, int b) {
        arr[a] = arr[a] + arr[b];
        arr[b] = arr[a] - arr[b];
        arr[a] = arr[a] - arr[b];
    }

    private static void shellSort(int[] array) {

        for (int step = array.length / 2; step > 0; step /= 2) {
            //遍历每一个组，进行 插入排序
            for (int i = 0; i < step; i++) {
                //每一个组内元素是：  i, i + step, i + 2*step ...
                for (int j = i + step; j < array.length; j += step) {
                    //选择一个元素j
                    int selectValue = array[j];
                    int k = j - step;
                    for (; k >= 0 && array[k] > selectValue; k -= step) {
                        array[k + step] = array[k];
                    }
                    k += step;
                    array[k] = selectValue;
                }
            }
        }
    }

}