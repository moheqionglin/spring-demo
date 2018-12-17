package com.moheqionglin.aop.warnning4.biz;

import com.moheqionglin.aop.warnning4.BizAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 9:17 PM
 */
@Component
public class BizCode2 {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @BizAnno
    public void fun1(){
        log.info(" => [method] fun1");
        ((BizCode2)AopContext.currentProxy()).fun2();
    }


    @BizAnno
    public void fun2(){
        log.info(" => [method] fun2");
    }

}