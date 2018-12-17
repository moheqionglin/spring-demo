package com.moheqionglin.aop.warnning5Exception;

import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 17/12/2018 11:15 AM
 */
@Component
public class Biz {
    public void bizFunc(){

        int i = 1 / 0;
    }
}