package com.moheiqonglin.shard.multipleDatasource;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 07/12/2018 4:38 PM
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:multiple-datasource.xml");

        DatasourceRouter datasourceRouter = (DatasourceRouter) context.getBean("datasourceRouter");
//        executeSql(context);
        String url = "jdbc:mysql://127.0.0.1:3306/datasource5?characterEncoding=UTF-8&useSSL=false";
        datasourceRouter.setDynamicDs("product5", url, null, null);
        executeSql(context);


    }



    private static void executeSql(ClassPathXmlApplicationContext context) {
        Service service = (Service)context.getBean("service");

        service.doSomething("product1");
        service.doSomething("product2");
        service.doSomething("product4", "bbb");
        service.doSomething("product5");
//        service.doSomething("product3");
    }
}
