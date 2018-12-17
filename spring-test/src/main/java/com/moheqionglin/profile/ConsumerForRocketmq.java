package com.moheqionglin.profile;

/**
 * @author wanli.zhou
 * @description
 * @time 13/12/2018 11:12 PM
 */
public class ConsumerForRocketmq implements IConsumer{
    @Override
    public void consumer() {
        System.out.println("> consumer from rocket mq");
    }
}