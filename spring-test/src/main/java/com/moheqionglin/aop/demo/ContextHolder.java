package com.moheqionglin.aop.demo;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 5:05 PM
 */
public class ContextHolder {
    private final static ThreadLocal<String> cache = new ThreadLocal<>();


    public static String getCache() {
//        System.out.println("=> Get " + cache.get());
        return cache.get();
    }
    public static void setCache(String cacheStr) {
        System.out.println("=> Set From " + cache.get() + " TO " + cacheStr);
        cache.set(cacheStr);
    }
    public static void clearCache() {
        System.out.println("=> remove from " + cache.get() + " to null");
        cache.remove();
    }

}