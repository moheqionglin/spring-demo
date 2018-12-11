package com.moheqionglin.simpleDemo;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author wanli zhou
 * @created 2018-06-24 8:38 PM.
 */
//@configurable 相当于 xml
@Configurable
@ComponentScan(basePackages = {"com.moheqionglin.demo"})
@PropertySource("classpath:application.properties")
public class Config {

    //@Bean相当于  xml的 bean标签，
    // 返回值相当于  <bean class="com.moheqionglin.services.TestBean"
    //方法名相当于  <bean id="testBean" /> 或者 @Bean（value = id）
    @Bean
    public TestBean tBean(){
        return new TestBean();
    }
}
