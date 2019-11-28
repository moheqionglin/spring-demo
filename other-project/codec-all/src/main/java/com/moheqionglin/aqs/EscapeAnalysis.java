package com.moheqionglin.aqs;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-28 15:08
 *
 * -server -XX:+DoEscapeAnalysis -XX:+PrintGCDetails -Xmx10m -Xms10m
 * -server -XX:-DoEscapeAnalysis -XX:+PrintGCDetails -Xmx10m -Xms10m
 *
 * 从jdk 1.7开始已经默认开始逃逸分析
 */
public class EscapeAnalysis{
    public void alloc() {
        byte[] b = new byte[2];
        b[0] = 1;
    }
    public void batchCreate(){
        long b = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        long e = System.currentTimeMillis();
        System.out.println(e - b);
    }
    public static void main(String[] args) {
        new EscapeAnalysis().batchCreate();
    }
}