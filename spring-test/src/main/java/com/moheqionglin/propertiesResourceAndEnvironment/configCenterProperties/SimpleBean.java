package com.moheqionglin.propertiesResourceAndEnvironment.configCenterProperties;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 10:31 AM
 */

public class SimpleBean {
    private String env;
    private String configValue;

    public SimpleBean(String env, String configValue) {
        this.env = env;
        this.configValue = configValue;
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

    @Override
    public String toString() {
        return "SimpleBean{" +
                "env='" + env + '\'' +
                ", configValue='" + configValue + '\'' +
                '}';
    }
}