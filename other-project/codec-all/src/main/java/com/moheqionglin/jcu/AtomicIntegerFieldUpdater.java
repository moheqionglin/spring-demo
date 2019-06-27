package com.moheqionglin.jcu;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-06-05 09:33
 */
public class AtomicIntegerFieldUpdater {
    public static void main(String[] args) throws NoSuchFieldException {
        new AtomicIntegerFieldUpdater().unsafeTest();
    }

    public void unsynTest(){
        Person p = new Person();
        for(int i = 0;i < 10; i ++){
            new Thread(() ->{
                for(int j = 0 ; j < 10 ; j ++){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    p.age = p.age + 1;
                    System.out.println(p.age);
                }

            }).start();
        }
    }

    public void unsafeTest() throws NoSuchFieldException {
        final Object maybeUnsafe = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                    unsafeField.setAccessible(true);
                    // the unsafe instance
                    return unsafeField.get(null);
                } catch (NoSuchFieldException e) {
                    return e;
                } catch (SecurityException e) {
                    return e;
                } catch (IllegalAccessException e) {
                    return e;
                }
            }
        });
        Unsafe unsafe = (Unsafe) maybeUnsafe;
        System.out.println(unsafe.addressSize());
        Person p = new Person();
        System.out.println(p);
        final long ageOffset = unsafe.objectFieldOffset(Person.class.getDeclaredField("age"));
        for(int i = 0 ; i < 10 ; i ++){
            new Thread(() -> {
                for(int j = 0 ; j < 10 ; j ++){
                    int age = p.age;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int newage = age + 1;
                    unsafe.compareAndSwapInt(p, ageOffset, age  , newage);
                    System.out.println(p);
                }


            }).start();
        }
    }


    class Person {
        volatile private int age = 0;

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    '}';
        }
    }
}