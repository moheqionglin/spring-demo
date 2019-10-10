//package com.moheqionglin.nonspring;
//
//import org.ehcache.Cache;
//import org.ehcache.CacheManager;
//import org.ehcache.PersistentCacheManager;
//import org.ehcache.config.builders.CacheConfigurationBuilder;
//import org.ehcache.config.builders.CacheManagerBuilder;
//import org.ehcache.config.builders.ResourcePoolsBuilder;
//import org.ehcache.config.units.EntryUnit;
//import org.ehcache.config.units.MemoryUnit;
//import org.ehcache.core.spi.service.StatisticsService;
//import org.ehcache.impl.internal.statistics.DefaultStatisticsService;
//
//import java.io.File;
//
///**
// * @author wanli.zhou
// * @description
// * @time 2019-09-19 19:47
// */
//public class OrgEcacheTest {
//    //http://www.ehcache.org/documentation/3.7/getting-started.html
//    public static void main(String[] args) throws InterruptedException {
//
//        StatisticsService statisticsService = new DefaultStatisticsService();
//
//
//        PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
//                .with(CacheManagerBuilder.persistence(new File("/Users/wanli.zhou/ecache/org/")))
//                .withCache("org_ecache_entity",
//                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Long.class,
//                                ResourcePoolsBuilder.newResourcePoolsBuilder()
//                                        .heap(10, EntryUnit.ENTRIES)
//                                        .offheap(1, MemoryUnit.MB)
//                                        .disk(200, MemoryUnit.MB, true)
//                        )
//                ).using(statisticsService)
//                .build(true);
//
//
//
//        Cache<String, Long> threeTieredCache = persistentCacheManager.getCache("org_ecache_entity", String.class, Long.class);
//
//        for(int i = 0 ; i < 1000; i ++){
//            threeTieredCache.put("k-" + i, Long.valueOf(i));
//        }
//
//        System.out.println(threeTieredCache.get("k-1"));
//
//        Thread.sleep(100000);
//
//    }
//}