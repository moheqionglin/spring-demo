package com.moheqionglin.dubbo.common;


import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @ClassName : UserServiceImpl
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-18 23:06
 */
@Service
public class UserServiceImpl implements UserService{
    AtomicInteger id = new AtomicInteger(1);
    List<User> users = new ArrayList<>();

    @Override
    public void create(User u) {
        u.setId(id.incrementAndGet());
        users.add(u);
        System.out.println("create user success " + u);
    }

    @Override
    public void delete(int userid) {
        User user = findById(userid);
        users.remove(user);
        System.out.println("delete success " + userid);
    }

    @Override
    public List<User> findByName(String name) {
        List<User> collect = users.stream().filter(u -> u.getName().contains(name)).collect(Collectors.toList());
        if(collect.isEmpty()){
            throw new RuntimeException("not exists");
        }
        System.out.println("find user success " + collect);
        return collect;
    }

    @Override
    public User findById(int userid) {
        User user = users.stream().filter(u -> u.getId() == userid).findAny().orElseGet(null);
        if(user == null){
            throw new RuntimeException("not exists");
        }
        return user;
    }
}