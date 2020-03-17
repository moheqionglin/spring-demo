package com.moheqionglin.dubbo.SPI.impls.ioc;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 22:29
 */
@SPI
public interface IocAnimal {

    @Adaptive("animalType")
    public void move(URL url);
}