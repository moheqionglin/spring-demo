package com.moheqionglin.beandefinitionRegistorPostProcessor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Configurable
@ComponentScan(basePackageClasses = BeanDefinitionRegistorPostProcessorConfig.class)
@Import({
                ColorImportBeanDefinitionRegistrar.class
})
public class BeanDefinitionRegistorPostProcessorConfig {

    @Bean
    public Color color(){

        Color c = new Color("颜色");
        return c;
    }
}
