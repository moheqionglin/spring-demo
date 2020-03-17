package com.moheqionglin.classLoader.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-02 16:10
 */
public class Main {
    public static void main(String[] args) {

        ServiceLoader<SipInterface> load = ServiceLoader.load(SipInterface.class);

        for(Iterator<SipInterface> iterator = load.iterator(); iterator.hasNext();){
            iterator.next().doSomething();
        }
    }
}