package com.moheqionglin.dubbo.startup.java;

import org.apache.dubbo.config.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 17:44
 */

/**
 * 当一致性hash的形式， retry必须设置为0,
 *  客户端的配置会覆盖服务端配置，比如客户端负载均衡设置为 一致性hash，那么就可以直接使用一致性hash
 *
 * 配置覆盖关系：
 *
 *         +-----------------------------------------+
 *          | +---------+   +---------+   +---------+ |
 * Provider | | method  +-->+interace +---> global  | |
 *          | +---------+   +---------+   +---------+ |
 *          +-----------------------------------------+
 *                 ^             ^             ^
 *                 | 覆盖          | 覆盖          | 覆盖
 *          +-----------------------------------------+
 *          | +---------+   +---------+   +---------+ |
 * Consumer | | method  +--->interface+---> global  | |
 *          | +---------+   +---------+   +---------+ |
 *          +-----------------------------------------+
 *
 * 1. 相同级别配置， consumer 覆盖 provider
 * 2. 不同级别， 按照method 覆盖 interface 覆盖 global
 *
 * example：
 *    Provider 配置了 method A 超时 1s
 *    consumer 没有配置method，但是配置了interface的超时时间2s。 那么 A的最终还是 1s
 *
 *
 *
 * */
public class DubboUtils {
    public static <T> void exportServiceWithZk(Class<T> clazz, Object clazzImpl, String appName, String registryAddress, Integer port, String group, String version){
        ApplicationConfig application = new ApplicationConfig();
        application.setName(appName);

        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setClient("curator");
        registry.setAddress(registryAddress);

        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(port);
        protocol.setThreads(20);

        ProviderConfig provider = new ProviderConfig();
        provider.setTimeout(3000);

        ServiceConfig<T> service = new ServiceConfig();
        service.setApplication(application);
        service.setRegistry(registry);
        service.setProtocol(protocol);
        service.setProvider(provider);
        service.setInterface(clazz);
        service.setRef((T) clazzImpl);
        service.setGroup(group);
        service.setVersion(version);
        service.setLoadbalance("roundrobin");
        service.export();
    }

    public static <T> void exportServiceUseConsistionHashWithZk2(Class<T> clazz, Object clazzImpl, String appName, String address, Integer port, String group, String consistentMethodName, String consistentMethodArguments) {
        ApplicationConfig application = new ApplicationConfig();
        application.setName(appName);
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(address);
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(port);
        ProviderConfig provider = new ProviderConfig();
        provider.setTimeout(3000);


        ServiceConfig<T> service = new ServiceConfig();
        service.setApplication(application);
        service.setRegistry(registry);
        service.setProtocol(protocol);
        service.setProvider(provider);
        service.setInterface(clazz);
        service.setRef((T) clazzImpl);
        service.setGroup(group);
        service.setLoadbalance("roundrobin");
        service.setMethods(consistentConfigs(consistentMethodName, consistentMethodArguments));
        service.export();

    }

    public static <T> void exportServiceInJVM(Class<T> clazz, Object clazzImpl, String appName, Integer port, String registryAddress, String group, String version){

        ApplicationConfig application = new ApplicationConfig();
        application.setName(appName);

        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(port);
        protocol.setThreads(20);

        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setClient("curator");
        registry.setAddress(registryAddress);

        ProviderConfig provider = new ProviderConfig();
        provider.setTimeout(3000);

        ServiceConfig<T> service = new ServiceConfig();
        service.setApplication(application);
        service.setProtocol(protocol);
        service.setProvider(provider);
        service.setRegistry(registry);
        service.setInterface(clazz);
        service.setRef((T) clazzImpl);
        service.setGroup(group);
        service.setVersion(version);
        service.setLoadbalance("roundrobin");
        service.export();
    }

    public static <T> T getReferenceInJVM(Class<T> clazz, String appName,  String group, String version, boolean async) {

        ApplicationConfig application = new ApplicationConfig();
        application.setName(appName);

        ConsumerConfig consumer = new ConsumerConfig();

        ReferenceConfig<T> reference = new ReferenceConfig<T>();
        reference.setApplication(application);
        reference.setProtocol("injvm");
        reference.setConsumer(consumer);
        reference.setInterface(clazz);
        reference.setGroup(group);
        reference.setLoadbalance("roundrobin");
        reference.setVersion(version);
        if (async) {
            reference.setAsync(async);
        }
        reference.setTimeout(3000);

        return reference.get();

    }

    /**
     *
     * @param consistenMethodName such add
     * @param arguments 0,1,2...
     * */
    private static List<MethodConfig> consistentConfigs(String consistenMethodName, String arguments) {

        List<MethodConfig> methods = new ArrayList();
        MethodConfig method = new MethodConfig();
        method.setName(consistenMethodName);
        method.setLoadbalance("consistenthash");
        Map<String, String> parameters = new HashMap();
        parameters.put("hash.arguments", arguments);
        //虚拟节点个数
        parameters.put("hash.nodes", "512");
        method.setParameters(parameters);
        methods.add(method);
        return methods;
    }

    public static <T> T getReferenceFromZk(Class<T> clazz, String appName, String address, String group, String version,
                                           boolean async, String loadbalance,
                                           int retry, String clusterFailStrenty,
        int timeout) {

        ApplicationConfig application = new ApplicationConfig();
        application.setName(appName);

        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setAddress(address);
        registry.setClient("curator");

        ConsumerConfig consumer = new ConsumerConfig();

        ReferenceConfig<T> reference = new ReferenceConfig<T>();
        reference.setApplication(application);
        reference.setCluster(clusterFailStrenty);
        reference.setRegistry(registry);
        reference.setConsumer(consumer);
        reference.setRetries(retry);
        reference.setInterface(clazz);
        reference.setGroup(group);
        reference.setLoadbalance(loadbalance);
        reference.setVersion(version);
        reference.setAsync(async);
        reference.setTimeout(timeout);
        return reference.get();
    }

    public static <T> T getReferenceUseConsistionHashFromZk(Class<T> clazz, String appName, String address, String group, String version, boolean async, String consistenMethodName, String arguments) {

        ApplicationConfig application = new ApplicationConfig();
        application.setName(appName);

        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setAddress(address);
        registry.setClient("curator");

        ConsumerConfig consumer = new ConsumerConfig();

        ReferenceConfig<T> reference = new ReferenceConfig<T>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setConsumer(consumer);
        reference.setInterface(clazz);
        reference.setGroup(group);
        reference.setLoadbalance("roundrobin");
        reference.setVersion(version);
        reference.setAsync(async);
        //一致性hash 必须设置 retry 为0
        reference.setRetries(0);
        reference.setTimeout(3000);
        reference.setMethods(consistentConfigs(consistenMethodName, arguments));
        return reference.get();
    }

    public static <T> T getReferenceP2PDirect(Class<T> clazz, String appName, String group, String ip, Integer port, String version) {
        ApplicationConfig application = new ApplicationConfig();
        application.setName(appName);

        ConsumerConfig consumer = new ConsumerConfig();

        ReferenceConfig<T> reference = new ReferenceConfig<T>();
        reference.setApplication(application);
        reference.setConsumer(consumer);
        reference.setInterface(clazz);
        reference.setGroup(group);
        reference.setUrl("dubbo://" + ip + ":" + port);
        reference.setVersion(version);
        reference.setTimeout(3000);

        return reference.get();
    }

}