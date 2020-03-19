package com.moheqionglin.dubbo.startup.annotation;

import com.moheqionglin.dubbo.common.User;
import com.moheqionglin.dubbo.common.UserService;
import com.moheqionglin.dubbo.startup.xml.ConsumerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName : XmlConsumerMain
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 16:40
 */
public class AnnotationConsumerMain {
    public static void main(String[] args) throws InterruptedException {

        AnnotationConfigApplicationContext consumerContext = new AnnotationConfigApplicationContext(ConsumerAnnotationConfig.class);
        ConsumerAction consumerAction = (ConsumerAction) consumerContext.getBean("consumerAction");
        UserService userService = consumerAction.getUserService();
        userService.create(new User(null, "name-1", "code-1", "上海市", 30));

        for (int i = 0 ; i < 1000; i ++){
            System.out.println(userService.findByName("name-1"));
            Thread.sleep(1000);
        }
    }
}