package com.moheqionglin.dubbo.startup;

import com.moheqionglin.dubbo.common.User;
import com.moheqionglin.dubbo.common.UserService;
import com.moheqionglin.dubbo.startup.annotation.ConsumerAction;
import com.moheqionglin.dubbo.startup.annotation.ConsumerAnnotationConfig;
import com.moheqionglin.dubbo.startup.java.DubboUtils;
import com.moheqionglin.dubbo.startup.xml.ConsumerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName : AllConsumer
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 20:42
 */
public class AllConsumer {


    public static void main(String[] args) throws InterruptedException {
//        init();
        simpleJavaConsumer();

    }

    //一致性hash 只往一台机器上初始化
    private static void init() {
        //Class<T> clazz, String appName, String address, String group, String version, boolean async, String consistenMethodName, String arguments
        UserService userService = DubboUtils.getReferenceUseConsistionHashFromZk(UserService.class, "willyApp", "127.0.0.1:2181/dubbo", "g-1", "1.0.0", false, "create", "0");
        for(int i = 0 ; i < 3; i ++){
            userService.create(i, new User(null, "name-" + i, "code-" + i, i % 2 == 0 ? "上海市" : "北京市", 30 + i));
        }

    }


    public static void xmlConsumer() throws InterruptedException {

        AnnotationConfigApplicationContext consumerContext = new AnnotationConfigApplicationContext(ConsumerConfig.class);
        UserService userService = (UserService) consumerContext.getBean("userService");
        for (int i = 0 ; i < 1000; i ++){
            System.out.println(userService.findByName("name-1"));
            Thread.sleep(1000);
        }
    }

    public static void simpleJavaConsumer() throws InterruptedException {
        //"broadcast" failover
        //
        UserService userService = DubboUtils.getReferenceFromZk(UserService.class, "willyApp",
                "127.0.0.1:2181/dubbo", "g-1", "1.0.0",
                false, "roundrobin", 10, "failover", 500);
        for (int i = 0 ; i < 10; i ++){
            try{
                System.out.println(userService.findByName("name-1"));
            }catch (Exception e){

                System.out.println("not EXIST!!");
            }

            Thread.sleep(1000);
        }
    }
    public static void annotationConsumer() throws InterruptedException {

        AnnotationConfigApplicationContext consumerContext = new AnnotationConfigApplicationContext(ConsumerAnnotationConfig.class);
        ConsumerAction consumerAction = (ConsumerAction) consumerContext.getBean("consumerAction");
        UserService userService = consumerAction.getUserService();

        for (int i = 0 ; i < 1000; i ++){
            System.out.println(userService.findByName("name-1"));
            Thread.sleep(1000);
        }
    }
}