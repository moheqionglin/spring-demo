package com.moheqionglin.scalapackage;

import javafx.scene.Parent;

import java.util.stream.IntStream;

/**
 * @author wanli.zhou
 * @description
 * @time 02/04/2019 5:48 PM
 */
public class T {
    public static void main(String[] args) {
        int reduce = IntStream.of(1, 2, 3, 4, 5).reduce(0, (x, y) -> {
            return x + y;
        });

        System.out.println(reduce);

        IntStream intStream = IntStream.of(1, 2, 3, 4, 5).map(x -> x * 3);
        System.out.println(intStream.toArray().toString());


        System.out.println();
        Child c = new Child();
        System.out.println();
        Child c1 = new Child("--");

        System.out.println();

        Parent1 p2 = new Child();
        Child c2 = new Child();
        System.out.println(p2.name);
        System.out.println(c2.name);

        Super sp = new Sub();
        Sub sub = new Sub();
        System.out.println(sp.s);
        System.out.println(sub.s);

    }

    public <T extends Number>void s(T a){

    }

}

class Super {
    String s = "super";
}

class Sub extends Super{
    String s = "Sub";
}

class Parent1 {
    String name = "super";

    public Parent1(){
        System.out.println("parent 无参构造器");
    }

    public Parent1(String name){
        System.out.println("parent 无参构造器 " + name);
    }
}

 class Child extends Parent1 {
    String name = "sub";
    public Child(){
        System.out.println("Child 无参构造器");
    }
    public Child(String name){
        System.out.println("Child 无参构造器" + name);
    }

}