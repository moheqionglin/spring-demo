package com.moheqionglin.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-05-31 09:54
 */
public class FanXing {
    public static void main(String[] args) {

        //区分List List<Object> List<?>
        test1();

        //区分 List<? extends T>, List<? super T>
        test2();
    }

    //https://blog.csdn.net/qq_37964379/article/details/88528997
    private static void test2() {
        //List<? extends T> 上限是T 也就是放的元素都是T的子类
        //List<? super T> 下限是T， 也就是放的元素都是T的父类

        //<? extends T>的场景是put功能受限, put被禁止
        //<? super T>的场景是get功能受限， get被禁止
        class Animal{protected String name = "animal";

            @Override
            public String toString() {
                return "Animal{" +
                        "name='" + name + '\'' +
                        '}';
            }
        }
        class Dog extends Animal{private String name1 = "Dog";

            @Override
            public String toString() {
                return "Dog{" +
                        "name='" + super.name + '\'' +
                        ", name1='" + name1 + '\'' +
                        '}';
            }
        }
        class Cat extends Animal{private String name2 = "Cat";

            @Override
            public String toString() {
                return "Cat{" +
                        "name='" + super.name + '\'' +
                        ", name2='" + name2 + '\'' +
                        '}';
            }
        }
        class JinMao extends Dog{private String name3 = "JinMao";

            @Override
            public String toString() {
                return "JinMao{" +
                        "name='" + super.name + '\'' +
                        ", name1='" + super.name1 + '\'' +
                        ", name3='" + name3 + '\'' +
                        '}';
            }
        }

        List<Dog> list1_1 = Stream.of(new Dog()).collect(Collectors.toList());
        List<Cat> list1_2 = Stream.of(new Cat()).collect(Collectors.toList());
        List<JinMao> list1_3 = Stream.of(new JinMao()).collect(Collectors.toList());

        //-----测试赋值----
        List<? extends Dog> list2_1 = list1_1;
        List<? super Dog> list2_2 = list1_1;

        //赋值的时候 只能赋值 JinMao和超类的，但是add的时候， 只能add JinMao和 JinMao子类的。
        List<? super JinMao> list2_3 = list1_3;
        List<? super JinMao> list2_4 = list1_1;

        //----测试add <? extends T>的 不能做任何的add操作，除非add null
//        list2_1.add(new JinMao());
//        list2_1.add(new Cat());
//        list2_1.add(new Dog());

        //----测试 add  <? super T> 可以add
        list2_2.add(new Dog());
//        list2_2.add(new Animal());
        list2_2.add(new JinMao());


        //类型擦除，但是不是对象擦除， 里面对象还是原来的对象，只是集合不认识这个类型了。所以是类型擦除
        //----测试 get  <? super T> 只能get 出来Object
        Object object = list2_2.get(0);
        System.out.println(list2_2.get(0).getClass());
        System.out.println(list2_2);


    }

    /*
    * 区分List List<Object> List<?>
    * */
    public static void test1(){
        //***************************************
        //*****List、List<Object>、List<?> 区别****
        //*****区别主要两种：1.赋值，2.元素增删改******
        //***************************************

        //--------------1----------------
        //List 1.元素增删改没限制
        List list = new ArrayList();
        list.add("xxx");
        list.add(1L);
        list.add(2);
        list.add(true);
        //List 2. 元素赋值 没限制
        List<Object> list1_1 = new ArrayList<>();
        List list1_2 = list1_1;

        //--------------2----------------
        //List<Object> 1.元素增删改没限制
        List<Object> list2_1 = new ArrayList<>();
        list2_1.add("xxx");
        list2_1.add(1L);
        list2_1.add(2);
        list2_1.add(true);
        list2_1.add(Arrays.asList(new Object[]{"aaa", 56.5f, false}));
        //List<Object>  2.但是赋值有限制 报错xxxx
        List<Integer> list2_2 = new ArrayList<>();
        //有问题
        //List<Object> list2_3 = list2_2;

        //--------------3----------------
        //List<?> 1. 元素增不允许。删改允许。只能接受赋值常用来做形参，或者类的变量。
        List<?> list3_1 = new ArrayList<>();

        //报错xxxx
//        list3_1.add("xxxx");
        //List<?> 元素赋值没限制
        List<?> list4_1 = list2_1;
        list4_1.addAll(list);
        //赋值结束也不能增加新元素
        //list4_1.add('c');
        System.out.println(list4_1);

    }
}