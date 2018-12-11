package com.moheqionglin.dynamicInjectBean;

import com.moheqionglin.simpleDemo.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 05/12/2018 4:37 PM
 */
@Component
public class DynamicBean {
    @Autowired
    private TestDao testDao;

    public void print(){
        System.out.println("-->Test " + testDao.getName());
    }


}