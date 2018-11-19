package customRpc.protocol;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import customRpc.reflect.Calculator;
import customRpc.reflect.CalculatorImpl;

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

        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}