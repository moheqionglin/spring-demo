package com.moheqioinglin.agent.test;

public class AgentTest {

    public static void main(String[] args) {
        String userId = "100001";
        queryUserAge(userId);
        queryUserName(userId);
    }

    private static void queryUserAge(String userId) {
        try {
            Thread.sleep(300);
            System.out.println("hello userId：" + userId + " age 18");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void queryUserName(String userId) {
        try {
            Thread.sleep(100);
            System.out.println("hello userId：" + userId + " name agent");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}