package com.moheqionglin.dubbo.SPI.impls.ioc;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.SPI;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 22:30
 */
@SPI
public interface IocPerson {
    public void walk(URL url);
}