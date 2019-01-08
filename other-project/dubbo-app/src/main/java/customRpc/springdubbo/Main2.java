package customRpc.springdubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import customRpc.api.Another;
import customRpc.api.AnotherImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 07/01/2019 4:17 PM
 */
public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("---------->");
        ClassPathXmlApplicationContext applicationContext2 = new ClassPathXmlApplicationContext("classpath:dubbo-config-context2.xml");
        applicationContext2.start();

        try{
            consumerFromInJvm();
        }catch (Exception e){
            e.printStackTrace();
        }

        AnotherImpl anotherImpl = (AnotherImpl) applicationContext2.getBean("anotherImpl");
        anotherImpl.print("---");
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


        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
//
//        // 引用远程服务
//        ReferenceConfig<Calculator> reference = new ReferenceConfig<Calculator>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
//        reference.setApplication(application);
//        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
//        reference.setInterface(Calculator.class);
//        reference.setVersion("1.0.0");
//
//        RpcContext.getContext().setAttachment("test", "===>");
//        // 和本地bean一样使用xxxService
//        Calculator xxxService = reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
//
//        System.out.println(xxxService.add(1, 4));

    }
}