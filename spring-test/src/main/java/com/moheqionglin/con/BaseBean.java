package com.moheqionglin.con;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BaseBean {

    @Value("${locationx.service.rocketmq.address}")
    private String mqAddress;

    @Value("${abc}")
    private String name = "abcd";


    public BaseBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMqAddress() {
        return mqAddress;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "mqAddress='" + mqAddress + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public void setMqAddress(String mqAddress) {
        this.mqAddress = mqAddress;
    }
}
