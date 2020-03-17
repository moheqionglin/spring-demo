package com.moheqionglin.dubbo.SPI.impls.aop;

import com.moheqionglin.dubbo.SPI.impls.Animal;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 21:47
 */
public class AopDog implements AopAnimal{

    @Override
    public void move() {
        System.out.println("狗狗 四条腿爬");
    }
}