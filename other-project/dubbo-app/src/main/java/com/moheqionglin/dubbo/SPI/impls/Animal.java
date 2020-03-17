package com.moheqionglin.dubbo.SPI.impls;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 21:47
 */
//默认实现是bird
@SPI("bird")
public interface Animal {
    public void move();
}