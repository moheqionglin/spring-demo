package com.moheqionglin.dubbo.SPI.impls.aop;

import com.moheqionglin.dubbo.SPI.impls.Animal;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 21:49
 */
public class AopBird implements AopAnimal{

    @Override
    public void move() {
        System.out.println("小鸟飞着走");
    }
}