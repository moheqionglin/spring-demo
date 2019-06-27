package com.moheqionglin.con;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

public class DataBasePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor{

    @Override
    public Properties mergeProperties(){
        Properties p = new Properties();
        p.setProperty("test.properties", "万里");
//        p.setProperty("abc", "万里");
        return p;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        super.postProcessBeanFactory(beanFactory);
    }

}
