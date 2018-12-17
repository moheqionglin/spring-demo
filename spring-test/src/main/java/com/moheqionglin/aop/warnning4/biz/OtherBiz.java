package com.moheqionglin.aop.warnning4.biz;

import com.moheqionglin.aop.warnning4.BizAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 13/12/2018 1:58 PM
 */
@Component
public class OtherBiz {

    @Autowired
    private BizCode1 bizCode1;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @BizAnno
    public void print(){
        log.info("OtherBiz->print");
        bizCode1.fun2();
    }

}