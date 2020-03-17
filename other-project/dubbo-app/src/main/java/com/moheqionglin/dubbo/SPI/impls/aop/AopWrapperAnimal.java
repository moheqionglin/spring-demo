package com.moheqionglin.dubbo.SPI.impls.aop;

import com.moheqionglin.dubbo.SPI.impls.Animal;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 22:12
 */
public class AopWrapperAnimal implements AopAnimal{

    private AopAnimal animal;

    public AopWrapperAnimal(AopAnimal animal) {
        this.animal = animal;
    }

    @Override
    public void move() {
        System.out.println("Before move");
        animal.move();
        System.out.println("After move");
    }
}