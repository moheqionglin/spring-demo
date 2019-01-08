package customRpc.springdubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.RpcContext;
import customRpc.api.Another;
import customRpc.api.Calculator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 07/01/2019 11:05 AM
 */
public class DifferentDubboConfigMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--->>>");
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:dubbo-config-context1.xml");
        applicationContext.start();
        System.out.println("===?");

        ClassPathXmlApplicationContext applicationContext2 = new ClassPathXmlApplicationContext("classpath:dubbo-config-context2.xml");
        applicationContext2.start();
        System.out.println("===?");
        try{
            consumerFromInJvm();
        }catch (Exception e){
            e.printStackTrace();
        }

        Thread.sleep(10000000);
    }

    private static void consumerFromInJvm() {
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("spring-dubbo-wanli1");
        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");

        ReferenceConfig<Another> reference2 = new ReferenceConfig<Another>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference2.setApplication(application);
        reference2.setGroup("group-2");
        reference2.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference2.setInterface(Another.class);
        Another another = reference2.get();
        another.print("lala ");


    }
}