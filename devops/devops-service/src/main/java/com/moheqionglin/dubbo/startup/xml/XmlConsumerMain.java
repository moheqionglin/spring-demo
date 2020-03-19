package com.moheqionglin.dubbo.startup.xml;

import com.moheqionglin.dubbo.common.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName : XmlConsumerMain
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 16:40
 */
public class XmlConsumerMain {
    public static void main(String[] args) throws InterruptedException {

        AnnotationConfigApplicationContext consumerContext = new AnnotationConfigApplicationContext(ConsumerConfig.class);
        UserService userService = (UserService) consumerContext.getBean("userService");
        for (int i = 0 ; i < 1000; i ++){
            System.out.println(userService.findByName("name-1"));
            Thread.sleep(1000);
        }
    }
}