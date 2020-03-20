package com.moheqionglin.dubbo.common;


import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @ClassName : UserServiceImpl
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-18 23:06
 */
@Service(version = "1.0.0", group = "g-1")
public class UserServiceImpl implements UserService{
    AtomicInteger id = new AtomicInteger(1);
    List<User> users = new ArrayList<>();

    @Override
    public void create(int requestid, User u) {
        u.setId(id.incrementAndGet());
        users.add(u);
        System.out.println("requestid = "+requestid+", create user success " + u);
    }

    @Override
    public void delete(int userid) throws InterruptedException {
        User user = findById(userid);
        users.remove(user);
        System.out.println("delete success " + userid);
    }

    @Override
    public List<User> findByName(String name) throws InterruptedException {
        List<User> collect = users.stream().filter(u -> u.getName().contains(name)).collect(Collectors.toList());
        if(collect.isEmpty()){
            System.out.println(">>>>>>>>>find user error ");
//            Thread.sleep(1000);
            throw new RuntimeException("not exists");
        }
        System.out.println(">>>>>>>>>find user success " + collect);
        return collect;
    }

    @Override
    public User findById(int userid) throws InterruptedException {
        User user = users.stream().filter(u -> u.getId() == userid).findAny().orElseGet(null);
        if(user == null){
            throw new RuntimeException("not exists");
        }
        return user;
    }

    public UserServiceImpl() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
                    System.out.println("========================");
                    System.out.println(users);
                    System.out.println("========================");
                },
                10, 5, TimeUnit.SECONDS);
    }
}