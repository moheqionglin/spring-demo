package com.moheqionglin.dubbo.startup.java;

import com.moheqionglin.dubbo.common.User;
import com.moheqionglin.dubbo.common.UserService;

/**
 * @ClassName : ConsumerMain
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 16:41
 */
public class ConsumerMain {
    public static void main(String[] args) throws InterruptedException {

        UserService userService = DubboUtils.getReferenceFromZk(UserService.class, "willyApp", "127.0.0.1:2181/dubbo", "g-1", "1.0.0", false);
        userService.create(new User(null, "name-1", "code-1", "上海市", 30));

        for (int i = 0 ; i < 1000; i ++){
            System.out.println(userService.findByName("name-1"));
            Thread.sleep(1000);
        }


    }
}