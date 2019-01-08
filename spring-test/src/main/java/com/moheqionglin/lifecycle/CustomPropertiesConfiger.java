package com.moheqionglin.lifecycle;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.util.Properties;

/**
 * @author wanli.zhou
 * @description
 * @time 03/01/2019 10:43 AM
 */
//@Component
public class CustomPropertiesConfiger extends PropertyPlaceholderConfigurer {

    @Override
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);

        System.out.println("==》==》==》" + props);
        // -------这里写从配置中心拉去配置，然后覆盖掉其他配置。
        Properties p = new Properties();
        p.setProperty("lifecycle.other", "**[" + props.getProperty("lifecycle.other") + "]**");
        props.putAll(p);

    }

}