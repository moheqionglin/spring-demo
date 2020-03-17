package com.moheqionglin.dubbo.SPI;

import com.moheqionglin.dubbo.SPI.impls.Animal;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 21:46
 */
public class JavaSPI {
    public static void main(String[] args) {
        ServiceLoader<Animal> load = ServiceLoader.load(Animal.class);
        for(Iterator<Animal> iterator = load.iterator(); iterator.hasNext(); ){
            Animal animal = iterator.next();
            System.out.print(animal + "\t");
            animal.move();
        }
    }
}