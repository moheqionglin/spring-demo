package com.moheiqonglin.performentWrite;

import com.moheiqonglin.shard.ShardingDao;
import com.moheiqonglin.shard.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 10/10/2018 2:17 PM
 */
public class ShardingMain {
    //https://stackoverflow.com/questions/13564627/spring-aop-not-working-for-method-call-inside-another-method
    public static void main(String[] args) {
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoConfig.class);
            WriteDao writeDao = (WriteDao) applicationContext.getBean("writeDao");
            writeDao.write(80000000);
            System.out.println("完成");
            applicationContext.close();
    }
}