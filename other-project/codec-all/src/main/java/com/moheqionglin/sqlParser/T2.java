package com.moheqionglin.sqlParser;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-10 18:58
 */
public class T2 {
    private static String a=null;

    static {
        try {
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void init() throws InterruptedException {
        Thread.sleep(1000);
        a = "xxx";
    }


    public static void call(){
        System.out.println(a);
    }
}