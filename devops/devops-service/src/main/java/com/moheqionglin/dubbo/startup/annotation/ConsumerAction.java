package com.moheqionglin.dubbo.startup.annotation;

import com.moheqionglin.dubbo.common.UserService;
import org.apache.dubbo.config.annotation.Reference;

/**
 * @ClassName : ConsumerAction
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 17:53
 */
public class ConsumerAction {
    @Reference(interfaceClass = UserService.class, group = "g-1", version = "1.0.0")
    private UserService userService;


    public UserService getUserService() {
        return userService;
    }
}