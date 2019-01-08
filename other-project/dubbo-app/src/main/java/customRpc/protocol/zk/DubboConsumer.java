package customRpc.protocol.zk;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.RpcContext;
import customRpc.api.Calculator;

/**
 * @author wanli.zhou
 * @description
 * @time 19/11/2018 1:53 PM
 */
public class DubboConsumer {

    public static void main(String[] args) {
        consumerFromZk();


    }

    private static void consumerFromZk() {
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