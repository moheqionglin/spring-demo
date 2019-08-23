package com.moheqionglin;

import java.util.Random;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-18 18:56
 *
 * a & a = a   a & 0 = 0
 * a | a = a   a | 0 = a
 * a ^ a = 0   a ^ 0 = a
 *
 */
public class BitYunSuan {
    public static void main(String[] args) {
        System.out.println("=====");
        weiyunsuanqumo();

        System.out.println("======");
        xor();
        weiyi();
        System.out.println(-1 ^ 5);
        System.out.println(~5);
        //5 - 7 = 5 + (-7) = 5 + ~6
        System.out.println(5 + ~6);

        xor4();
    }

    /*
    *
    * 位运算取模的时候， 如果被模除的数是 2的幂次方，那么可直接 & (x -1)。 因为 x - 1高位全为0，低位全为1. 求&以后，结果相当于模除。
    * */
    private static void weiyunsuanqumo() {
        for(int i = 0; i < 100; i ++){
            int i1 = i & (4-1);
            int i2 = (i % 4);
            if(i1 != i2){
                System.out.println(i1 + " " + i2);
            }

        }
    }

    public static void weiyi(){
        System.out.println(Long.MAX_VALUE >> 32);
        System.out.println(Integer.MAX_VALUE);

        System.out.println(-1 >> 31);
        System.out.println(1 >> 31);
    }

    /*
    *
    * 任何数 A A ^ A = 0 A ^ 0 = A
    * 交换律  A ^ B = B ^ A
    * 结合律  (A ^ B) ^ C = A ^ (B ^ C)
    * 自反性 A ^ B ^ B = A
    *
    * */
    public static void xor(){
        System.out.println(3 ^ 3);
        System.out.println(3 ^ 0);

        //应用1 应用自反性 交换两个数
        swap(10, 3);
        //1-1000方在1001的数组中， 只有一个数重复了，找出来
        xor1();
        //判断奇数偶数
        jioushu(3);
    }

    //奇数最低位为1  1 & 1 = 0
    private static void jioushu(int i) {
        int a = i &1;
        System.out.println(i+ " 是 " + (a == 1 ? "奇数" : "偶数"));
    }

    //1-1000方在1001的数组中， 只有一个数重复了，找出来
    //一个数组存放若干整数，一个数出现奇数次，其余数均出现偶数次，找出这个出现奇数次的数
    //一个数组里除了一个数字之外，其他的数字都出现了两次。请写程序找出这个只出现一次的数字
    private static void xor1() {
        int arr[] = new int[1001];
        for(int i = 1 ; i <= 1000; i ++){
            arr[i - 1] = i;
        }
        arr[1000] = 99;

        //O(1)空间复杂度， O(n)时间复杂度
        int a = 0;
        for(int i = 1; i <= 1000; i ++){
            a = a ^ i ^ arr[i-1];
        }
        a = a ^ arr[1000];
        System.out.println("重复的数是 " + a);
    }

   /*
    *一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
    */
    public static void xor4(){
        int arr[] = {1,2,3,4,5,6,1,2,3,4,5,9};
        int num1 = 0, num2 = 0;


        int xorResult  = 0;
        for(int it : arr){
            xorResult ^= it;
        }
        //如果数组中只有两个数不同，那么在xorResult中的第N位一定为 1.  我们把数组arr 中元素在第N位为1的拎出来。 那么这个集合一定是包含一个数。其他都是相同的。
        int pos = 0;
        while (true){
            if(((xorResult >> 1) & 1 )== 1){
                pos ++;
                break;
            }
        }
        for(int i = 0 ; i < arr.length ; i ++){
            if(((arr[i] >> pos) & 1 ) == 1){
                num1 ^= i;
            }
        }
        num2 = num1 ^ xorResult;
        System.out.println(num1 + " 和 " + num2);
    }

     private static void swap(int a, int b) {
        System.out.println(a + " " + b);
        a = a ^ b;
        //b = a ^ b ^ b = a
        b = a ^ b;
        //a = a ^ b ^ a = b
        a = a ^ b;
        System.out.println(a + " " + b);
    }
}