package com.moheqionglin.dubbo.SPI.impls.ioc;

import com.alibaba.dubbo.common.URL;
import com.moheqionglin.dubbo.SPI.impls.Animal;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 21:49
 */
public class IocBird implements IocAnimal{

    @Override
    public void move(URL url) {
        System.out.println("小鸟飞着走");
    }
}