package com.moheiqonglin.shard.multipleDatasource.aop;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Map;


/**
 * @author wanli.zhou
 * @description
 * @time 07/12/2018 4:04 PM
 */
public class DatasourceRouter extends AbstractRoutingDataSource implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    protected Object determineCurrentLookupKey() {
        String datasourceKey = DatasourceHolder.getDatasourceKey();
        System.out.println("==>{}" +  datasourceKey );
        return datasourceKey;
    }

    public void setDynamicDs(String product, String url, String userName, String passwd) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        DatasourceHolder.addDsKeys(product);

        Class<DatasourceRouter> clazz = (Class<DatasourceRouter>) this.getClass();
        Class<AbstractRoutingDataSource> clazz2 = AbstractRoutingDataSource.class;

        Field resolvedDataSources = clazz2.getDeclaredField("resolvedDataSources");
        resolvedDataSources.setAccessible(true);
        Map<Object, DataSource> dsMap = (Map<Object, DataSource>) resolvedDataSources.get(this);

        DruidDataSource ds1 = (DruidDataSource) applicationContext.getBean("product1");
        DruidDataSource druidDataSource = new DruidDataSource();
        BeanUtils.copyProperties(ds1, druidDataSource);
        druidDataSource.setUrl(url);

        dsMap.put("product5", druidDataSource);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}