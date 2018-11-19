package customRpc.protocol;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.Channel;
import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.remoting.exchange.ExchangeChannel;
import com.alibaba.dubbo.remoting.exchange.ExchangeClient;
import com.alibaba.dubbo.remoting.exchange.ExchangeHandler;
import com.alibaba.dubbo.remoting.exchange.Exchangers;
import com.alibaba.dubbo.remoting.exchange.support.ExchangeHandlerAdapter;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboInvoker;
import com.alibaba.dubbo.rpc.proxy.javassist.JavassistProxyFactory;
import customRpc.reflect.Calculator;
import customRpc.reflect.CalculatorImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author wanli.zhou
 * @description
 * @time 19/11/2018 2:42 PM
 */
public class SelfDubbo {


    /**
     * 满眼都是 Invoker， dubbo 在export服务的时候，会在内部维护一个 Map<Key, Inboker>的map，然后客户端通过url，找到url对应的invoker实现调用
     * @param args
     */
    public static void main(String[] args) throws RemotingException {
        localMockConsumerInvoker();

    }


    private static void localMockConsumerInvoker() throws RemotingException {
        //provider
        Calculator calculatorImpl = new CalculatorImpl();
        JavassistProxyFactory javassistProxyFactory = new JavassistProxyFactory();

        //"injvm://127.0.0.1/customRpc.reflect.Calculator?anyhost=true&application=xxx&dubbo=2.5.5&generic=false&interface=customRpc.reflect.Calculator&methods=add,sub&pid=35677&revision=1.0.0&side=provider&threads=200&timestamp=1542609918615&version=1.0.0"
        //URL(String protocol, String username, String password, String host, int port, String path, Map<String, String> parameters)
        Map<String, String> params = new HashMap<>();
        params.put("registry", "zookeeper");
        params.put("application", "yyy");
        params.put("refer", "application%3Dyyy%26dubbo%3D2.5.5%26interface%3DcustomRpc.reflect.Calculator%26methods%3Dadd%2Csub%26pid%3D35874%26revision%3D1.0.0%26side%3Dconsumer%26timestamp%3D1542610978674%26version%3D1.0.0");
        params.put("client", "curator");
        params.put("dubbo", "2.5.5");
        params.put("pid", "35874");
        params.put("timestamp", "1542610978739");

        URL url = new URL("registry", "127.0.0.1", 2181, "customRpc.reflect.Calculator", params);

        Set<Invoker<?>> invokerss = new HashSet<>();
        DubboInvoker<Calculator> dInvoker = new DubboInvoker<Calculator>(Calculator.class, url, new ExchangeClient[]{Exchangers.connect(url)}, invokerss);
        invokerss.add(dInvoker);

        Calculator proxy = javassistProxyFactory.getProxy(dInvoker, new Class<?>[]{Calculator.class});
        int add = proxy.add(1, 4);
        System.out.println(add);
        //  dInvoker.
//        Invoker<Calculator> invoker = javassistProxyFactory.getInvoker(null, Calculator.class, url);
//        RpcInvocation invocation = new RpcInvocation("add", new Class<?>[]{int.class, int.class}, new Object[]{1, 5});
//        Result result = invoker.invoke(invocation);

//        System.out.println(result.getValue());
    }


    private static void localInvoker() {
        //provider
        Calculator calculatorImpl = new CalculatorImpl();
        JavassistProxyFactory javassistProxyFactory = new JavassistProxyFactory();

        //"injvm://127.0.0.1/customRpc.reflect.Calculator?anyhost=true&application=xxx&dubbo=2.5.5&generic=false&interface=customRpc.reflect.Calculator&methods=add,sub&pid=35677&revision=1.0.0&side=provider&threads=200&timestamp=1542609918615&version=1.0.0"
        //URL(String protocol, String username, String password, String host, int port, String path, Map<String, String> parameters)
//        URL url = new URL("injvm1", "127.0.0.1", 0, "customRpc.reflect.Calculator?anyhost=true&application=xxx&dubbo=2.5.5&generic=false&interface=customRpc.reflect.Calculator&methods=add,sub&pid=35677&revision=1.0.0&side=provider&threads=200&timestamp=1542609918615&version=1.0.0");
        Invoker<Calculator> invoker = javassistProxyFactory.getInvoker(calculatorImpl, Calculator.class, null);
        RpcInvocation invocation = new RpcInvocation("add", new Class<?>[]{int.class, int.class}, new Object[]{1, 5});
        Result result = invoker.invoke(invocation);

        System.out.println(result.getValue());
    }

}