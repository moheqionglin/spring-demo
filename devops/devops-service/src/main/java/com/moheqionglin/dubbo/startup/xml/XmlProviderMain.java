package com.moheqionglin.dubbo.startup.xml;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName : XmlMain
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 16:22
 */
public class XmlProviderMain {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext providerContext = new AnnotationConfigApplicationContext(ProviderConfig.class);

        synchronized (XmlProviderMain.class){
            XmlProviderMain.class.wait();
        }

    }
}