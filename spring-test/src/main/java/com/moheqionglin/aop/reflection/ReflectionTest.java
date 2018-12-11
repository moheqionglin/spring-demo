package com.moheqionglin.aop.reflection;

import java.lang.reflect.Field;

/**
 * @author wanli.zhou
 * @description
 * @time 10/12/2018 2:08 PM
 */
public class ReflectionTest {

    /***
     * getDeclaredField  获取
     *
     *
     */
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        Children children = new Children();

        Class<Parent> pclazz = Parent.class;
        Class<Children> chClass = Children.class;

        Field parentVarField = pclazz.getDeclaredField("parentVar");
        parentVarField.setAccessible(true);

        Field childrenVarField = chClass.getDeclaredField("childrenVar");
        childrenVarField.setAccessible(true);

//        Field defaultVar = chClass.getField("defaultVar");
//        Field protectedVar = chClass.getField("protectedVar");
        Field publicVar = chClass.getField("publicVar");

        parentVarField.set(children, "父类属性");
        childrenVarField.set(children, "自己的属性");

        System.out.println(children);
    }
}


