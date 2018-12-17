package com.moheqionglin.aop.warnning4.biz;

import com.moheqionglin.aop.warnning4.BizAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 9:17 PM
 */
@Component
public class BizCode4 {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private BizCode4 bizCode4;

    @BizAnno
    public void fun1(){
        log.info(" => [method] fun1");
        bizCode4.fun2();
    }


    @BizAnno
    public void fun2(){
        log.info(" => [method] fun2");
    }

}