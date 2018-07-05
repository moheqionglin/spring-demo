package com.moheqionglin;

import com.moheqionglin.dao.BookDao;
import com.moheqionglin.service.BookService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@Configurable
@EnableCaching
@ComponentScan(basePackageClasses = {
        BookDao.class,
        BookService.class
})
public class CacheConfig {

    //有四类achemanager， concurrentMapCache一般在测试用， pd上一般用ecache
    @Bean
    public CacheManager cacheManager(){
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("book"),
                new ConcurrentMapCache("animal")));
        return simpleCacheManager;
    }
}
