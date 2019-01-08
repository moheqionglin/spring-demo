package customRpc.protocol.zk;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.rpc.RpcContext;
import customRpc.api.Calculator;
import customRpc.api.CalculatorImpl;

/**
 * @author wanli.zhou
 * @description
 * @time 09/11/2018 5:27 PM
 */
public class DubboServer {

    public static void main(String[] args) {
        // 服务实现
        CalculatorImpl xxxService = new CalculatorImpl();

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("xxx");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setAddress("127.0.0.1:2181");
        registry.setClient("curator");

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(9998);
        protocol.setThreads(200);

        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口

        // 服务提供者暴露服务配置
        ServiceConfig<Calculator> service = new ServiceConfig<>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(Calculator.class);
        service.setRef(xxxService);
        service.setVersion("1.0.0");

        // 暴露及注册服务
        service.export();

        consumerFromInJvm();
        System.out.println();

        System.out.println();
        System.out.println();

        DubboConsumer.main(null);

        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    //从 2.2.0 开始，每个服务默认都会在本地暴露。
    // http://dubbo.apache.org/zh-cn/docs/user/demos/local-call.html
    private static void consumerFromInJvm() {
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("yyy");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setAddress("127.0.0.1:2181");
        registry.setClient("curator");
        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接

        // 引用远程服务
        ReferenceConfig<Calculator> reference = new ReferenceConfig<Calculator>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(Calculator.class);
        reference.setVersion("1.0.0");

        RpcContext.getContext().setAttachment("test", "===>");
        // 和本地bean一样使用xxxService
        Calculator xxxService = reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用

        System.out.println(xxxService.add(1, 4));
    }

}