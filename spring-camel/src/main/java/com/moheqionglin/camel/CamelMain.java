package com.moheqionglin.camel;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.HashMap;
import java.util.Map;


public class CamelMain {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:a:b:c")
                        .id("a:b:c")
                        .process(new Processor(){

                            @Override
                            public void process(Exchange exchange) throws Exception {
                                System.out.println("==>" + exchange.getIn().getHeader("a") + ", " + exchange.getIn().getBody());
                            }
                        })
                        .log(LoggingLevel.INFO, "===>>>> ${header.a}, ${body}")
                        .end();
            }
        });

        context.start();

        ProducerTemplate producer = context.createProducerTemplate();
        Map<String, Object> header = new HashMap<>();
        header.put("a", "header里面东西");
        producer.sendBodyAndHeaders("direct:a:b:c",  ExchangePattern.InOnly, "body里面的东西", header);
        Thread.sleep(10 * 1000);
    }
}
