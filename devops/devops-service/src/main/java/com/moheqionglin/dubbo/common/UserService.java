package com.moheqionglin.dubbo.common;

import java.util.List;

/**
 * @ClassName : UserService
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-18 23:03
 */
public interface UserService {

    void create(User u);

    void delete(int userid);

    List<User> findByName(String name);

    User findById(int userid);
}