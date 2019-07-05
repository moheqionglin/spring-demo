package com.moheqionglin.alth.sort.selectSort;

import com.moheqionglin.alth.Util;

import java.util.Map;

/**
 * @author wanli.zhou
 * @description
 * @time 13/01/2019 9:34 PM
 */
//https://blog.csdn.net/qq_35178267/article/details/78313306
public class HeapSort {


    public static void main(String[] args) {

        int array[] = Util.mockArray(10);
        Util.print(array);

//        System.out.println("===最大堆===");
//        MaxHeap.buildMaxHeap(array, array.length);
//        Util.print(array);


        System.out.println();
        System.out.println("===堆排序 小->大===");
        MaxHeap.heapSort(array);
        Util.print(array);

    }


    /**
     *  堆分为大堆和小堆
     *  且： 堆一般用数据表示。 对于一个 从0下标开始的堆来说，
     *          父节点：  ( i - 1) / 2
     *          自己： i
     *          左孩子： i*2 + 1
     *          右边孩子： i*2 + 2
     *
     */
    //最大堆
    private static class MaxHeap{

        //从小到大
        public static void heapSort(int [] array){
            buildMaxHeap(array, array.length);

            for(int i = array.length -1; i > 0 ; i --){
                swap(array, 0, i);
                adjustmaxHeap(array, 0, i -1);
            }
        }
        /**
         *
         * @param array     无序堆数组
         * @param i         当先需要调整的元素的小标
         * @param heapSize  heap-size[A]: 存放在数组A中堆的元素的个数，是要排序的元素的个数，在进行堆排序时，这个是会变的(减1)
         */
        public static void adjustmaxHeap(int array[], int i , int heapSize){

            //i 的 左孩子 2 * i + 1, 右孩子 2 * i + 2
            int maxIndex = i;
            int leftChildIndex = leftChild(i);
            int rightChildIndex = rightChild(i);

            if(existsChild(leftChildIndex, heapSize) && array[maxIndex] < array[leftChildIndex]){
                maxIndex = leftChildIndex;
            }
            if(existsChild(rightChildIndex , heapSize) && array[maxIndex] < array[rightChildIndex]){
                maxIndex = rightChildIndex;
            }

            if(maxIndex != i){
                swap(array, maxIndex, i);
                adjustmaxHeap(array, maxIndex, heapSize);
            }

        }

        /**
         *
         * @param array
         * @param heapSize
         *
         * 构建一个最大堆， 是从第一个非叶子节点开始 --一直到-> 0(也就是根节点)（因为第一个飞叶子节点有自己的孩子可以比较）， 递归构建的
         */
        public static void buildMaxHeap(int [] array, int heapSize){
            for(int i = (heapSize - 2) / 2; i >= 0; i --){
                adjustmaxHeap(array, i, heapSize);
            }
        }

//        public static int parent(int i){
//            return (i - 1) / 2;
//        }
        public static int leftChild(int i){
            return i * 2 + 1;
        }
        public static int rightChild(int i){
            return i * 2 + 2;
        }
        private static void swap(int[] array, int maxIndex, int i) {
            int tmp = array[maxIndex];
            array[maxIndex] = array[i];
            array[i] = tmp;
        }

        private static boolean existsChild(int leftChildIndex, int heapSize) {
            return leftChildIndex < heapSize;
        }


    }
}