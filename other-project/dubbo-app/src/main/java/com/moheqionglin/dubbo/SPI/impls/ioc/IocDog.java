package com.moheqionglin.dubbo.SPI.impls.ioc;

import com.alibaba.dubbo.common.URL;
import com.moheqionglin.dubbo.SPI.impls.Animal;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 21:47
 */
public class IocDog implements IocAnimal{

    @Override
    public void move(URL url) {
        System.out.println("狗狗 四条腿爬");
    }
}