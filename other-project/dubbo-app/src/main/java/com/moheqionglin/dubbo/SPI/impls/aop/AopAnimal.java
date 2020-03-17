package com.moheqionglin.dubbo.SPI.impls.aop;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 21:47
 */
@SPI
public interface AopAnimal {
    public void move();
}