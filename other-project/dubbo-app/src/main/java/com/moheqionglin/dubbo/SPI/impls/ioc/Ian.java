package com.moheqionglin.dubbo.SPI.impls.ioc;

import com.alibaba.dubbo.common.URL;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 21:57
 */
public class Ian implements IocPerson{

    private IocAnimal animal;

    public void setAnimal(IocAnimal animal) {
        this.animal = animal;
    }

    @Override
    public void walk(URL url) {
        animal.move(url);
    }
}