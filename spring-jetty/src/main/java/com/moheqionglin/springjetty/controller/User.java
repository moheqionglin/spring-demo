package com.moheqionglin.springjetty.controller;

public class User {
    private String username;
    private Integer age;
    private boolean privacyEnable;
    private String method;
    private String headerPlainText;
    public User() {
    }

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    public String getHeaderPlainText() {
        return headerPlainText;
    }

    public void setHeaderPlainText(String headerPlainText) {
        this.headerPlainText = headerPlainText;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isPrivacyEnable() {
        return privacyEnable;
    }

    public void setPrivacyEnable(boolean privacyEnable) {
        this.privacyEnable = privacyEnable;
    }
}