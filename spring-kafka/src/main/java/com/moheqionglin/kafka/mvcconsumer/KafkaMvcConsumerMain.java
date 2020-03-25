package com.moheqionglin.kafka.mvcconsumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

/**
 * Kafka 消费一个新topic，或者 用一个新的group消费一个老的topic的话， 默认是从最近的一条消息（也就是，consumer脸上kafka见到的一条消息开始消费。）
 * <p>
 * 后面， 如果消费以后， 冲洗程序，那么就会从上一次程序停止的那个地方开始消费。
 * <p>
 * <p>
 * **********************************************************
 * Kafka的 consumer offset会定位到 最近一次ACk的那个地方
 * **********************************************************
 * partition-0： 300 303 306 309
 * partition-1： 301 304 307
 * partition-2： 302 305 308
 * <p>
 * if(value1.getId() == 306 || value1.getId() == 304 || value1.getId() == 305){
 * acknowledgment.acknowledge();
 * }
 * 我们只ACK 306， 304， 305 那么不管是否ACK了303，301，302那么offset都会重置到 306，307，308，程序重启以后再消费
 * 会从这以后开始消费。
 * <p>
 * **********************************************************
 * **********************************************************
 */
public class KafkaMvcConsumerMain {

    public static void main(String[] args) throws InterruptedException {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KafkaMvcConsumerConfig.class);
        // 服务实现
        MessageController xxxService = new MessageControllerImpl();

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("xxx");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(9988);
        protocol.setThreads(200);

        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口

        // 服务提供者暴露服务配置
        ServiceConfig<MessageController> service = new ServiceConfig<>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(MessageController.class);
        service.setRef(xxxService);
        service.setVersion("1.0.0");

        // 暴露及注册服务
        service.export();

    }
}
