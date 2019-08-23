package com.moheqionglin.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-31 11:03
 */
@Component
public class CustomDisponsableBean implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("CustomDisponsableBean.destroy ");

    }
}