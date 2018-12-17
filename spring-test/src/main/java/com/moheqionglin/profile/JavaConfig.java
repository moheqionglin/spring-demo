package com.moheqionglin.profile;

import org.springframework.context.annotation.*;

/**
 * @author wanli.zhou
 * @description
 * @time 13/12/2018 11:12 PM
 */
@Configuration
@ComponentScan(basePackages = "com.moheqionglin.profile")
@PropertySource("classpath:profile.properties")
public class JavaConfig {

    @Profile("kafka")
    @Bean(name = "consumer")
    public IConsumer kafkaBean(){
        return new ConsumerForKafka();
    }

    @Profile("rocketMq")
    @Bean(name = "consumer")
    public IConsumer rocketMqBean(){
        return new ConsumerForRocketmq();
    }
}