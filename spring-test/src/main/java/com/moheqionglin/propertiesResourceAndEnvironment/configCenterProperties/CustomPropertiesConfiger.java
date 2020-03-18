package com.moheqionglin.propertiesResourceAndEnvironment.configCenterProperties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author wanli.zhou
 * @description
 * @time 03/01/2019 10:43 AM
 */
public class CustomPropertiesConfiger extends PropertyPlaceholderConfigurer implements EnvironmentAware {
    private Environment environment;
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {

        props.put("config.value", ">||>> " +props.get("config.value") );

        super.processProperties(beanFactoryToProcess, props);

    }

    @Override
    protected void loadProperties(Properties props) throws IOException {

        super.loadProperties(props);

        if ("dev".equalsIgnoreCase(this.environment.getActiveProfiles()[0])) {
            Properties p = new Properties();
            p.setProperty("config.value1", "dev从 配置中心读取-" + StringUtils.trimArrayElements(this.environment.getActiveProfiles()));
            props.putAll(p);
            props.put("config.value", props.get("config.value") + "> dev配置中心修改后");
        }else if("default".equalsIgnoreCase(this.environment.getActiveProfiles()[0])){
            Properties p = new Properties();
            p.setProperty("config.value1", "default从 配置中心读取-" + StringUtils.trimArrayElements(this.environment.getActiveProfiles()));
            props.putAll(p);
            props.put("config.value", props.get("config.value") + "> default配置中心修改后");
        }

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}