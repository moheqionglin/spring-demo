package com.moheqionglin.dynamicParam;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 15:38
 */
public class DubboContext {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getAppid(){
        return threadLocal.get();
    }

    public static void setAppid(String appid){
        threadLocal.set(appid);
    }
}