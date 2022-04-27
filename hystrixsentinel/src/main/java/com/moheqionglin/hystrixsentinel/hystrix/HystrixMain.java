package com.moheqionglin.hystrixsentinel.hystrix;

import com.moheqionglin.hystrixsentinel.biz.UserController;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class HystrixMain {
    public static void main(String[] args) {
        UserController userController = new UserController();

        //HystrixCommandGroupKey group, HystrixThreadPoolKey threadPool, int executionIsolationThreadTimeoutInMilliseconds
        UserControllerHystrixCommand command = new UserControllerHystrixCommand(userController,
                HystrixCommandGroupKey.Factory.asKey("userControllerGroupKey"),
                HystrixThreadPoolKey.Factory.asKey("userControllerThreadPoolKey"),
                1000
                );

//        command.execute();
        for (int i = 0; i < 10; i++) {
                command.execute();
        }
    }
}
