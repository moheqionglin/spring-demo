package com.moheqionglin.classLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-02 14:57
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        loadClassTest();
        loadClassTestWithDifferentLoaderName();
    }

    private static void loadClassTestWithDifferentLoaderName() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String path = Main.class.getClassLoader().getResource(".").getPath();
        path = path + "classloader/test/";

        MoheClassLoader loader1 = new MoheClassLoader("", path);
        MoheClassLoader loader2 = new MoheClassLoader("", path);

        Class<?> aClass1 = loader1.loadClass("com.moheqionglin.classLoader.Person");
        Class<?> aClass2 = loader2.loadClass("com.moheqionglin.classLoader.Person");

        Object p1 = aClass1.newInstance();
        Object p2 = aClass2.newInstance();

        Method toPersonMethod = aClass1.getMethod("toPerson", Object.class);
        toPersonMethod.invoke(p1, p2);
    }

    private static void loadClassTest() throws ClassNotFoundException {
        String path = Main.class.getClassLoader().getResource(".").getPath();
        path = path + "classloader/test/";
        MoheClassLoader loader = new MoheClassLoader("loader1", path);
        System.out.println("loader parent is " + loader.getParent());
        Class<?> aClass = loader.loadClass("com.moheqionglin.classLoader.Person");
        System.out.println(aClass.getClassLoader());
    }
}