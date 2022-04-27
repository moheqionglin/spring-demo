package com.moheqionglin.hystrixsentinel.biz;

import com.moheqionglin.hystrixsentinel.domain.User;

import java.util.Arrays;
import java.util.List;

public class UserRpcClient {
    List<User> users = Arrays.asList(new User[]{
            new User("姓名-1", "地址-1", 10),
            new User("姓名-2", "地址-2", 20),
            new User("姓名-3", "地址-3", 30),
    });
    public List<User> queryUsers(){
        return users;
    }

    public void addUsers(User user){
        users.add(user);
    }
}
