package com.moheqionglin.dubbo.SPI.impls;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 21:47
 */
public class Dog implements Animal{

    @Override
    public void move() {
        System.out.println("狗狗 四条腿爬");
    }
}