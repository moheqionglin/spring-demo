package com.moheqionglin.kafka.mvcconsumer;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 07/11/2018 9:50 AM
 */

public interface MessageController {

    public List<String> readMessage();
}