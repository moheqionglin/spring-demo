package com.moheqionglin.dubbo.common;

import java.util.List;

/**
 * @ClassName : UserService
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-18 23:03
 */
public interface UserService {

    void create(int requestid , User u);

    void delete(int userid) throws InterruptedException;

    List<User> findByName(String name) throws InterruptedException;

    User findById(int userid) throws InterruptedException;
}