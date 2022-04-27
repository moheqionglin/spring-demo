package com.moheqionglin.aop.demo;

public class Calc {
    public static void main(String[] args) {
        new Calc().func();
    }
    public void func() {
        int a = 0, b = 0, c;

        func1(a, b);
        c = a + b;
    }

    public void func1(int a, int b){
        int x = 0;
        func2(x);
        x = x + 10086;
    }

    public void func2(int x){
        int m, n;

    }

}
