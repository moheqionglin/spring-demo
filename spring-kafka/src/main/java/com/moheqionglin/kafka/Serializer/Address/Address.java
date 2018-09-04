package com.moheqionglin.kafka.Serializer.Address;

import java.io.Serializable;

public class Address implements Serializable {
    private Long id;
    private String province;
    private String city;
    private String county;

    public Address(Long id, String province, String city, String county) {
        this.id = id;
        this.province = province;
        this.city = city;
        this.county = county;
    }

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                '}';
    }
}