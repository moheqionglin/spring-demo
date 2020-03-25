package com.moheqionglin.kafka.common.person;

import com.moheqionglin.kafka.common.Address.Address;
import java.io.Serializable;
import java.util.Date;

public class Person  implements Serializable {
    private Long id;
    private String name;
    private Date birthday;
    private int age;
    private boolean isMalel;
    private Long othre;
    private Address address;

    public Person(Long id, String name, Date birthday, int age, boolean isMalel, Long othre, Address address) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.age = age;
        this.isMalel = isMalel;
        this.othre = othre;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMalel() {
        return isMalel;
    }

    public void setMalel(boolean malel) {
        isMalel = malel;
    }

    public Long getOthre() {
        return othre;
    }

    public void setOthre(Long othre) {
        this.othre = othre;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", age=" + age +
                ", isMalel=" + isMalel +
                ", othre=" + othre +
                ", address=" + address +
                '}';
    }
}
