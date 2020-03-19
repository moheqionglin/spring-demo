package com.moheqionglin.dubbo.startup.java;

import com.moheqionglin.dubbo.common.UserService;
import com.moheqionglin.dubbo.common.UserServiceImpl;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName : ProviderMain
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 16:41
 */
public class ProviderMain {
    public static void main(String[] args) throws InterruptedException {
        DubboUtils.exportServiceWithZk(UserService.class, new UserServiceImpl(), "willyApp", "127.0.0.1:2181/dubbo", 8181, "g-1", "1.0.0");
        new CountDownLatch(1).await();
    }
}