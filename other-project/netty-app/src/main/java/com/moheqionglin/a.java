package com.moheqionglin;

import io.netty.util.AbstractReferenceCounted;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-06-04 23:07
 */
public class a {
    public static void main(String[] args) {
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

        try {
            Person p = new Person();
            System.out.println(p);
            long ageOffset = unsafe.objectFieldOffset(Person.class.getDeclaredField("age"));

            unsafe.compareAndSwapInt(p, ageOffset, 0  , 1);
            System.out.println(p);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    static class Person {
        private int age = 0;

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    '}';
        }
    }
}