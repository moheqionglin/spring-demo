package com.moheqionglin.alth.recurceVSStack;

import com.moheqionglin.alth.Util;

import java.util.Stack;

/**
 * @author wanli.zhou
 * @description
 * @time 13/01/2019 9:16 AM
 */
public class Recure {

    public static void main(String[] args) {
        int[] array = Util.mockArray(10);
        Util.print(array);

        reversePrint(array,-1);
        System.out.println();
        printUseStac(array);
    }

    private static void printUseStac(int[] array) {
        Stack<Integer> stack = new Stack<>();

        for(int i = 0 ; i < array.length; i ++){
          stack.push(array[i]);
        }

        for(;!stack.isEmpty();){
            System.out.print(stack.pop() + "\t");
        }
    }


    private static void reversePrint(int[] array, int start) {
        if(start >= array.length - 1){
            return ;
        }
        reversePrint(array, ++start);
        System.out.print(array[start] + "\t");
    }


}