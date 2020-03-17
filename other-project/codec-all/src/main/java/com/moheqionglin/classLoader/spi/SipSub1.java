package com.moheqionglin.classLoader.spi;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-02 16:09
 */
public class SipSub1 implements SipInterface{
    @Override
    public void doSomething() {
        System.out.println("SipSub1 class");
    }
}