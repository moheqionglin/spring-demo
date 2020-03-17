package com.moheqionglin.PropertySourcesPropertyResolverTest;

import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-02 20:08
 */
public class Main {
    public static void main(String[] args) throws IOException {
        PropertiesPropertySource resourcePropertySource = new ResourcePropertySource("applicationproperties", "application.properties");
        MutablePropertySources ps = new MutablePropertySources();
        ps.addFirst(resourcePropertySource);
        PropertySourcesPropertyResolver propertyResolver = new PropertySourcesPropertyResolver(ps);

        String s = propertyResolver.resolvePlaceholders("${application.name}");
        System.out.println(s);
    }
}