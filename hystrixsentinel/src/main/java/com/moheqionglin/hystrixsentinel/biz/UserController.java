package com.moheqionglin.hystrixsentinel.biz;

import com.moheqionglin.hystrixsentinel.domain.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserController {
    List<User> users = Arrays.asList(new User[]{
            new User("姓名-1", "地址-1", 10),
            new User("姓名-2", "地址-2", 20),
            new User("姓名-3", "地址-3", 30),
    });
    private static AtomicInteger callCnt = new AtomicInteger(0);
    public List<User> queryUsers(){
        if(callCnt.incrementAndGet() % 2 == 0){
            throw new RuntimeException("mock error");
        }
        return users;
    }

    public void addUsers(User user){
        users.add(user);
    }
}
