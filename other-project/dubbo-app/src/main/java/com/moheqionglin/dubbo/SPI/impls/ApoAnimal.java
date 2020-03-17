package com.moheqionglin.dubbo.SPI.impls;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 22:12
 */
public class ApoAnimal implements Animal{

    private Animal animal;

    public ApoAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public void move() {
        System.out.println("Before move");
        animal.move();
        System.out.println("After move");
    }
}