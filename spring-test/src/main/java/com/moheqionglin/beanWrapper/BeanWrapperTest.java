package com.moheqionglin.beanWrapper;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-08-26 14:33
 */
public class BeanWrapperTest {


    public static void main(String[] args) {
        new BeanWrapperTest().doBeanWrapper();
    }

    public void doBeanWrapper(){
        Book javaInAction = new Book();
//        BeanWrapper javaInActionBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(javaInAction);
        BeanWrapper javaInActionBeanWrapper = new BeanWrapperImpl(javaInAction);
        javaInActionBeanWrapper.setPropertyValue("author", "周万里");
        javaInActionBeanWrapper.setPropertyValue("totalPage", 100);
        javaInActionBeanWrapper.setPropertyValue("price", 6.7d);
        System.out.println(javaInAction);
    }

}
