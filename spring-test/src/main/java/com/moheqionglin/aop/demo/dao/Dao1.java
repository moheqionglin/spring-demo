package com.moheqionglin.aop.demo.dao;

import com.moheqionglin.aop.demo.ContextHolder;
import com.moheqionglin.aop.demo.ValidCheck;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 5:04 PM
 */
@Component
public class Dao1 {

    //1.1 同类之间调用 有调用有
    @ValidCheck
    public void sameYou2YouSameProduct(String key){
        System.out.println("有 Dao1->sameYou2YouSameProduct : " + ContextHolder.getCache());
        function2(key);
    }
    //1.1 同类之间调用 有调用有
    @ValidCheck
    public void sameYou2YouDiffProduct(String key){
        System.out.println("有 Dao1->sameYou2YouDiffProduct : " + ContextHolder.getCache());
        function2(key + "-Diff");
    }

    @ValidCheck
    public void function2(String key){
        System.out.println("有 Dao1->function2 : " + ContextHolder.getCache());
    }



    //1.2 同类之间有->无
    @ValidCheck
    public void sameYou2WuSameProduct(String key){
        System.out.println("有 Dao1->sameYou2WuSameProduct : " + ContextHolder.getCache());
        function4(null);

    }
    public void function4(String key){
        System.out.println("无 Dao1->function4 : " + ContextHolder.getCache());
    }


    //1.3 同类之间无->有
    public void sameWu2YouSameProduct(String key){
        System.out.println("无 Dao1->sameWu2YouSameProduct : " + ContextHolder.getCache());
        function6("random-product");

    }

    @ValidCheck
    public void function6(String key){
        System.out.println("有 Dao1->function6 : " + ContextHolder.getCache());
    }

    //1.4 同类之间无->无
    public void sameWu2WuSameProduct(String key){
        System.out.println("无 Dao1->sameWu2WuSameProduct : " + ContextHolder.getCache());
        function8();

    }
    @ValidCheck
    public void function8(){
        System.out.println("无 Dao1->function8 : " + ContextHolder.getCache());
    }

}