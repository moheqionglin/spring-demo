package com.moheqionglin.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : Guava
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 20:01
 */
public class GuavaTest {

    private final Cache<String, String> cache1 = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();

    public String get(String key) throws ExecutionException {
        return cache1.get(key, ()->{
            //query from db;
            return "empty-"+key+" val";
        });
    }

    public static void main(String[] args) throws ExecutionException {
        GuavaTest guavaTest = new GuavaTest();
        System.out.println(guavaTest.get("k1"));
    }
}