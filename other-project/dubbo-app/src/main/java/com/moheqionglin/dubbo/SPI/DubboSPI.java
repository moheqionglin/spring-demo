package com.moheqionglin.dubbo.SPI;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.moheqionglin.dubbo.SPI.impls.Animal;
import com.moheqionglin.dubbo.SPI.impls.aop.AopAnimal;
import com.moheqionglin.dubbo.SPI.impls.ioc.IocPerson;

import java.util.Collections;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 21:55
 *
 * 加载顺序
 * META-INF/dubbo/internal
 * META-INF/dubbo/
 * META-INF/services/
 *
 * 也就是 META-INF/services/ 覆盖 META-INF/dubbo/ 覆盖 META-INF/dubbo/internal
 */
public class DubboSPI {
    public static void main(String[] args) {
//        dubboSPIIoc("iocBird");
        dubboSPIAop("aopDog");
//        dubboSPIDefaultImpl("true");
    }

    private static void dubboSPIIoc(String type) {
        ExtensionLoader<IocPerson> extensionLoader = ExtensionLoader.getExtensionLoader(IocPerson.class);
        IocPerson person = extensionLoader.getExtension("ian");
        URL url = new URL("", "", 99, Collections.singletonMap("animalType",type));
        person.walk(url);
    }

    private static void dubboSPIAop(String spiname) {
        ExtensionLoader<AopAnimal> extensionLoader = ExtensionLoader.getExtensionLoader(AopAnimal.class);
        AopAnimal bird = extensionLoader.getExtension(spiname);
        bird.move();
    }
    private static void dubboSPIDefaultImpl(String spiName) {
        ExtensionLoader<Animal> extensionLoader = ExtensionLoader.getExtensionLoader(Animal.class);
        Animal bird = extensionLoader.getExtension(spiName);
        bird.move();
    }



}