package com.moheiqonglin.persistence.jpa.entities;

import javax.persistence.*;

/**
 * @author wanli.zhou
 * @description
 * @time 28/12/2018 4:40 PM
 */
@Entity
@Table(name="users")
public class Users {
    @Id
    private Long id;

    private String name;
    private Boolean sex;

    public Users() {
    }

    public Users(Long id , String name, Boolean sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }

    @PreUpdate
    public void changeUserName(){
        this.name = name.toUpperCase();
    }
}