package customRpc.springdubbo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author wanli.zhou
 * @description
 * @time 07/01/2019 11:24 AM
 */
@Component
public class DubboBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println(configurableListableBeanFactory.getBeanDefinition("com.alibaba.dubbo.config.RegistryConfig").getPropertyValues());
    }
}