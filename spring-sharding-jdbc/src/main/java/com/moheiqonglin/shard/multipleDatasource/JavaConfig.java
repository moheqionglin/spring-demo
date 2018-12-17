package com.moheiqonglin.shard.multipleDatasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.moheiqonglin.shard.multipleDatasource.aop.DatasourceHolder;
import com.moheiqonglin.shard.multipleDatasource.aop.DatasourceRouter;
import com.moheiqonglin.shard.multipleDatasource.domain.SplitDbConfigBean;
import com.moheiqonglin.shard.multipleDatasource.domain.SplitDbConfigBeanRowMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 10:04 AM
 */
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = "com.moheiqonglin.shard.multipleDatasource")
@PropertySource("classpath:multipledatasource.properties")
public class JavaConfig implements BeanFactoryAware {

    @Value("${default.ds.url}")
    private String defaultUrl;
    @Value("${default.ds.user}")
    private String defaultUser;
    @Value("${default.ds.password}")
    private String defaultPassword;

    private DefaultListableBeanFactory beanFactory;

    @Bean
    public JdbcTemplate jdbcTemplate(DatasourceRouter datasourceRouter){
        return new JdbcTemplate(datasourceRouter);
    }

    @Bean
    public DatasourceRouter datasourceRouter(DruidDataSource datasourceProductDefault){
        DatasourceRouter datasourceRouter = new DatasourceRouter();
        datasourceRouter.setDefaultTargetDataSource(datasourceProductDefault);
        Map<Object, Object> dsMap = new HashMap<>();
        datasourceRouter.setTargetDataSources(dsMap);

        //get datasource from database config table
        Map<Object, Object> dbDsMap = initDatasourceFromDatabase(datasourceProductDefault);
        dsMap.putAll(dbDsMap);

        return datasourceRouter;
    }

    /**
     *
     * @param datasourceProductDefault
     * @return
     * 1. 从默认数据源的 表：
     */
    private Map<Object,Object> initDatasourceFromDatabase(DruidDataSource datasourceProductDefault) {
        Map<Object, Object> dsKey2DsMap = new HashMap<>();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasourceProductDefault);
        List<SplitDbConfigBean> splitDbConfigBeans = jdbcTemplate.query("select id, shard_key, url, userName, password from datasource_default.split_db_config", new SplitDbConfigBeanRowMapper());
        for(SplitDbConfigBean splitDbConfigBean : splitDbConfigBeans){
            System.out.println(splitDbConfigBean);
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);

            beanDefinitionBuilder.setInitMethodName("init");
            beanDefinitionBuilder.setDestroyMethodName("close");
            beanDefinitionBuilder.addPropertyValue("driverClassName", "com.mysql.jdbc.Driver");
            beanDefinitionBuilder.addPropertyValue("url", splitDbConfigBean.getUrl());
            beanDefinitionBuilder.addPropertyValue("username", splitDbConfigBean.getUserName());
            beanDefinitionBuilder.addPropertyValue("password", splitDbConfigBean.getPassword());
            beanDefinitionBuilder.addPropertyValue("initialSize", 2);
            beanDefinitionBuilder.addPropertyValue("minIdle", 2);
            beanDefinitionBuilder.addPropertyValue("maxActive", 5);
            beanDefinitionBuilder.addPropertyValue("validationQuery", "select 1 from information_schema.tables limit 1");
            beanDefinitionBuilder.addPropertyValue("testWhileIdle", true);
            beanDefinitionBuilder.addPropertyValue("testOnBorrow", false);
            beanDefinitionBuilder.addPropertyValue("testOnReturn", false);

            beanFactory.registerBeanDefinition(splitDbConfigBean.getShardKey(), beanDefinitionBuilder.getBeanDefinition());

            DatasourceHolder.addDsKeys(splitDbConfigBean.getShardKey());
            dsKey2DsMap.put(splitDbConfigBean.getShardKey(), beanFactory.getBean(splitDbConfigBean.getShardKey()));
        }

        return dsKey2DsMap;

    }

    @Bean(name = "datasource-default")
    public DruidDataSource datasourceProductDefault(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl(defaultUrl);
        druidDataSource.setUsername(defaultUser);
        druidDataSource.setPassword(defaultPassword);
        druidDataSource.setInitialSize(2);
        druidDataSource.setMinIdle(2);
        druidDataSource.setMaxActive(5);
        druidDataSource.setValidationQuery("select 1 from information_schema.tables limit 1");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        return druidDataSource;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }
}