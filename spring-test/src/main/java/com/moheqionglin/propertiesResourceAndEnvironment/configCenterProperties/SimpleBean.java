package com.moheqionglin.propertiesResourceAndEnvironment.configCenterProperties;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 10:31 AM
 */

public class SimpleBean {
    private String env;
    private String configValue;
    private String configValue1;

    public SimpleBean(String env, String configValue, String configValue1) {
        this.env = env;
        this.configValue = configValue;
        this.configValue1 = configValue1;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigValue1() {
        return configValue1;
    }

    public void setConfigValue1(String configValue1) {
        this.configValue1 = configValue1;
    }

    @Override
    public String toString() {
        return "SimpleBean{" +
                "env='" + env + '\'' +
                ", configValue='" + configValue + '\'' +
                ", configValue1='" + configValue1 + '\'' +
                '}';
    }
}