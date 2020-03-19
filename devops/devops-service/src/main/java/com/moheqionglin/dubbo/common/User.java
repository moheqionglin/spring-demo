package com.moheqionglin.dubbo.common;

import java.io.Serializable;

/**
 * @ClassName : User
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-18 23:04
 */
public class User implements Serializable {
    private Integer id;
    private String name;
    private String openCode;
    private String address;
    private int age;

    public User(Integer id, String name, String openCode, String address, int age) {
        this.id = id;
        this.name = name;
        this.openCode = openCode;
        this.address = address;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenCode() {
        return openCode;
    }

    public void setOpenCode(String openCode) {
        this.openCode = openCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", openCode='" + openCode + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }
}