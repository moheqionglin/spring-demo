package com.moheqionglin.alth.other;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-03 13:32
 */
public class Gcd {

    public static void main(String[] args) {
        int a = 12;
        int b = 18;
        System.out.println(a + ", " + b + " 的最大公约数" + gcd(12, 18));
        System.out.println(a + ", " + b + " 最小公倍数" + (a * b) /gcd(12, 18));
    }

    /*
    * 最大公约数
    * */
    public static int gcd(int a, int b){
        if(a / b == 0){
            int c = a;
            a = b;
            b = c;
        }
        int mod = a % b;
        if(mod == 0){
            return b;
        }
        return gcd(b, mod);
    }


}