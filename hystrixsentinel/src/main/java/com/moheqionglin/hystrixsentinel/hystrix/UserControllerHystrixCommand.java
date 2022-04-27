package com.moheqionglin.hystrixsentinel.hystrix;

import com.moheqionglin.hystrixsentinel.biz.UserController;
import com.moheqionglin.hystrixsentinel.domain.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

import java.util.List;

public class UserControllerHystrixCommand extends HystrixCommand<List<User>> {
    private UserController userController;
    protected UserControllerHystrixCommand(UserController userController, HystrixCommandGroupKey group, HystrixThreadPoolKey threadPool, int executionIsolationThreadTimeoutInMilliseconds) {
        super(group,
                threadPool,
                executionIsolationThreadTimeoutInMilliseconds);
        this.userController = userController;
    }

    @Override
    protected List<User> run() throws Exception {
        System.out.println("--hystrix command--" + Thread.currentThread().getName() + ", " + userController.queryUsers());
        return userController.queryUsers();
    }

    @Override
    protected List<User> getFallback() {
        System.out.println("Hello Failure queryUsers!");
        return null;
    }

}
