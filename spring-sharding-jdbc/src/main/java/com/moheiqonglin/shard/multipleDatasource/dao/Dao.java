package com.moheiqonglin.shard.multipleDatasource.dao;

import com.moheiqonglin.shard.multipleDatasource.aop.DatasourceKey;
import com.moheiqonglin.shard.multipleDatasource.domain.User;
import com.moheiqonglin.shard.multipleDatasource.domain.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 07/12/2018 4:33 PM
 */
@Component
public class Dao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> queryAllUsers(@DatasourceKey String product){
        List<User> users = jdbcTemplate.query("select * from users", new UserMapper());
        System.out.println(">>>>>>>>>>>>>>>>" + users);
        return users;
    }

    public List<User> queryAllUsers(String product, String bbb){
        List<User> users = jdbcTemplate.query("select * from users", new UserMapper());
        System.out.println("|||>>>>>>>>>>>>>>>>" + users);
        return users;
    }

}


