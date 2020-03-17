package com.moheqionglin.dubbo.SPI.impls;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 21:48
 */
public class Chicken implements Animal{
    @Override
    public void move() {
        System.out.println("小鸡 两条腿爬着走");
    }
}