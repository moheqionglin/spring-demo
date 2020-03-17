package com.moheqionglin.dubbo.SPI.impls;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 21:49
 */
public class Bird implements Animal{

    @Override
    public void move() {
        System.out.println("小鸟飞着走");
    }
}