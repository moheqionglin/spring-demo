package com.moheqionglin.aop.demo.dao;

import com.moheqionglin.aop.demo.ContextHolder;
import com.moheqionglin.aop.demo.ValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 5:04 PM
 */
@Component
public class Dao2 {

    @Autowired
    private Dao1 dao1;

    //1.1 不同类 有->有
    @ValidCheck
    public void diffYou2YouSameProduct(String key){
        System.out.println("Dao2->diffYou2YouSameProduct : " + ContextHolder.getCache());
        dao1.function2(key);
    }
    @ValidCheck
    public void diffYou2YouDiffProduct(String key){
        System.out.println("Dao2->diffYou2YouDiffProduct : " + ContextHolder.getCache());
        dao1.function2(key + "-diff");
    }

    //1.2 不同类 有->无
    @ValidCheck
    public void diffYou2WuSameProduct(String key){
        System.out.println("Dao2->diffYou2WuSameProduct : " + ContextHolder.getCache());
        dao1.function4(key +"- null");
    }

    //1.3 不同类 无->有
    public void diffWu2YouSameProduct(String key){
        System.out.println("Dao2->diffWu2YouSameProduct : " + ContextHolder.getCache());
        dao1.function2(key);
    }

    //1.3 不同类 无->无
    @ValidCheck
    public void diffWu2WuDiffProduct(String key){
        System.out.println("Dao2->diffWu2WuDiffProduct : " + ContextHolder.getCache());
        dao1.function4(key + "-diff");
    }
}