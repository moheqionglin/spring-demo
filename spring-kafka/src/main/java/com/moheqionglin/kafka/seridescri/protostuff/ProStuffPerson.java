package com.moheqionglin.kafka.seridescri.protostuff;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-05-29 15:36
 */
public class ProStuffPerson {
    private int id = 0;
    private String name = "";
    private long age = 0L;
    private float lon = 0F;
    private boolean success = false;

    public ProStuffPerson(int id, String name, long age, float lon, boolean success) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.lon = lon;
        this.success = success;
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

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ProStuffPerson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", lon=" + lon +
                ", success=" + success +
                '}';
    }
}