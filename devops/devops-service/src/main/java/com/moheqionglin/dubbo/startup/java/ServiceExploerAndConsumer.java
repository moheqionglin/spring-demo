package com.moheqionglin.dubbo.startup.java;

import com.moheqionglin.dubbo.common.User;
import com.moheqionglin.dubbo.common.UserService;
import com.moheqionglin.dubbo.common.UserServiceImpl;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName : ServiceExploer
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 15:54
 */
public class ServiceExploerAndConsumer {

    public static void main(String[] args) throws InterruptedException {
//        Class<T> clazz, Object clazzImpl, String appName, String registryAddress, Integer port, String group, String version
       exportService();
//        Class<T> clazz, String appName, String address, String group, String version, boolean async
        UserService userService = DubboUtils.getReferenceFromZk(UserService.class, "willyApp", "127.0.0.1:2181/dubbo", "g-1", "1.0.0", true);
//        Integer id, String name, String openCode, String address, int age
        userService.create(new User(null, "name-1", "code-1", "上海市", 30));
        System.out.println(userService.findByName("name"));

        new CountDownLatch(1).await();
    }

    private static void exportService() {
        DubboUtils.exportServiceWithZk(UserService.class, new UserServiceImpl(), "willyApp", "127.0.0.1:2181/dubbo", 8181, "g-1", "1.0.0");
    }

}