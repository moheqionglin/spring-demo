package com.moheqionglin.proxy.staticProxy;

import com.moheqionglin.proxy.Calc;
import com.moheqionglin.proxy.CalcImpl;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-30 16:30
 */
public class CalcStaticProxy implements Calc {
    private CalcImpl calc;
    public CalcStaticProxy(CalcImpl calc) {
        this.calc = calc;
    }

    @Override
    public int add(int a, int b) {
        System.out.println("static proxy 加入逻辑");
        return calc.add(a, b);
    }
}