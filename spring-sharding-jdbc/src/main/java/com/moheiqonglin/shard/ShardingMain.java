package com.moheiqonglin.shard;

import com.moheiqonglin.shard.domain.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 10/10/2018 2:17 PM
 */
public class ShardingMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-dao.xml");
        ShardingDao shardingDao = (ShardingDao) applicationContext.getBean("shardingDao");
        List<User> users = shardingDao.query();

        System.out.println(users.size());
        for(User user : users){
            System.out.println(user.getId() + "," + user.getCreatedTime());
        }
    }
}