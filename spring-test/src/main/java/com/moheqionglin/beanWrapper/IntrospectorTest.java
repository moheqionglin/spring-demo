package com.moheqionglin.beanWrapper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-08-26 14:40
 * java 内省 VS 反射。
 * 反射跟x光一样， 一目了然所有字段
 * 内省只处理 暴露出来的。比如要给属性设置值，那么必须要定义getter/setter
 *
 * https://www.toutiao.com/i6716088868698849803/
 */
public class IntrospectorTest {


    public static void main(String[] args) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        new IntrospectorTest().doIntrospector();
    }

    public void doIntrospector() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Book book = new Book();

        BeanInfo beanInfo = Introspector.getBeanInfo(Book.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            System.out.println(propertyDescriptor.getReadMethod());
            System.out.println(propertyDescriptor.getWriteMethod());

            switch (propertyDescriptor.getName()){
                case "author":
                    propertyDescriptor.getWriteMethod().invoke(book, "introspector-万里");
                    break;
                case "totalPage":
                    propertyDescriptor.getWriteMethod().invoke(book, 23);
                    break;
                case "price":
                    propertyDescriptor.getWriteMethod().invoke(book, 56);
                    break;
            }
        }

        System.out.println();
        System.out.println(book);
    }
}