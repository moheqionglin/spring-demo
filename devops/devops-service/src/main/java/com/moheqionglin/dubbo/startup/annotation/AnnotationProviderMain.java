package com.moheqionglin.dubbo.startup.annotation;

import com.moheqionglin.dubbo.startup.xml.ProviderConfig;
import com.moheqionglin.dubbo.startup.xml.XmlProviderMain;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName : XmlMain
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-19 16:22
 */
public class AnnotationProviderMain {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext providerContext = new AnnotationConfigApplicationContext(ProviderAnnotationConfig.class);
//        providerContext.start();
        synchronized (XmlProviderMain.class){
            XmlProviderMain.class.wait();
        }

    }
}