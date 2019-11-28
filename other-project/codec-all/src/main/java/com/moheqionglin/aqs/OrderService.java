package com.moheqionglin.aqs;

import org.jcodings.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-22 17:45
 */
@Component
public class OrderService {

    @Autowired
    private CustomAQSLock customAQSLock;


    public String descStock(){
        customAQSLock.lock();

        return null;

    }


}