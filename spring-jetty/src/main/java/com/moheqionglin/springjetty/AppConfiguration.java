package com.moheqionglin.springjetty;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = { "com.moheqionglin.springjetty" })
@Import({ MvcConfiguration.class })
@EnableAspectJAutoProxy
public class AppConfiguration {
    @Bean
    public HystrixCommandAspect hystrixCommandAspect() {
        return new HystrixCommandAspect();
    }

}
