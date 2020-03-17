package com.moheqionglin.dubbo.SPI.impls.aop;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-11 10:14
 */

public class AopWrapperAnimal2 implements AopAnimal{
    private AopAnimal aopAnimal;
    public AopWrapperAnimal2(AopAnimal aopAnimal) {
        this.aopAnimal = aopAnimal;
    }

    @Override
    public void move() {
        System.out.println("AopWrapperAnimal2 before aop");
        aopAnimal.move();
        System.out.println("AopWrapperAnimal2 after aop");
    }
}