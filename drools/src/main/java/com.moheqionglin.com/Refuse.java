package com.moheqionglin.com;

import java.io.Serializable;

/**
 * 拒件实体
 * @author Administrator
 *
 */
public class Refuse implements Serializable {

    /**
     * 年龄
     */
    private int age;
    /**
     * 工作城市
     */
    private String workCity;
    /**
     * 申请城市
     */
    private String applyCity;
    /**
     * 居住城市
     */
    private String addressCity;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getApplyCity() {
        return applyCity;
    }

    public void setApplyCity(String applyCity) {
        this.applyCity = applyCity;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    @Override
    public String toString() {
        return "Refuse [age=" + age + ", workCity=" + workCity + ", applyCity="
                + applyCity + ", addressCity=" + addressCity + "]";
    }
}

