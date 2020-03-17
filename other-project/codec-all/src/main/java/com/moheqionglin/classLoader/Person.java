package com.moheqionglin.classLoader;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-02 15:08
 */
public class Person {
    private String name;
    private int age;

    public Person toPerson(Object o){
        return (Person)o;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}