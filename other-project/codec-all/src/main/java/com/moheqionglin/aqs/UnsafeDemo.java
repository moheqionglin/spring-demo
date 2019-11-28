package com.moheqionglin.aqs;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-27 19:34
 */
public class UnsafeDemo {

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
    }
}