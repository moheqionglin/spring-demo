package com.moheqionglin.aop.warnning3.child;

import com.moheqionglin.aop.warnning3.SimpleValid;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 3:15 PM
 */
@Component
public class ChildClass {
    @SimpleValid("void valid")
    public String print(){
        System.out.println("ChildClass --> ");
        return "ChildClass.print.return";
    }

}