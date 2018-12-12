package com.moheiqonglin.shard.multipleDatasource.domain;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 10:02 AM
 */
public class User {
    private int id ;
    private String name;
    private boolean sex;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}