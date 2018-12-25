package com.moheqionglin.con;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configurable
@PropertySource("classpath:test.properties")
@Import(
        {ImportBean.class,
                BaseBean.class}
)
public class Config {


    @Bean
    public DataBasePropertyPlaceholderConfigurer dataBasePropertyPlaceholderConfigurer(){
        return new DataBasePropertyPlaceholderConfigurer();
    }
}
