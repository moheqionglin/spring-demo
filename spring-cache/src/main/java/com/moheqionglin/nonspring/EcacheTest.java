package com.moheqionglin.nonspring;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.Serializable;
import java.net.URL;
import java.util.Random;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-19 14:54
 */
public class EcacheTest {
    public static void main(String[] args) throws InterruptedException {
        //创建缓存管理器
        URL url = EcacheTest.class.getClassLoader().getResource("ehcache.xml");

        final CacheManager cacheManager = CacheManager.create(url);

        // 创建一个缓存实例（在配置文件中获取一个缓存实例）
        final Cache cache = cacheManager.getCache("entityCache");
        long startCache = System.currentTimeMillis();
        for(int i = 0 ; i < 10000; i ++){
            //gid + ename "D0000003EAU"
            final String key = "273910_D00" + ename(i);

            //他建一个数据容器
//            final Element putGreeting = new Element(key, 30000 + i);
            cache.put(new Element(key, i));
            // fetch fencex tags and construct simple entity
            cache.put(new Element(i, new SimpleEntity("xxxx", 333L, "xxx")));
            //将数据放入到缓存实例中
//            cache.put(putGreeting);

            if(i % 100 == 0){
                System.out.println("cache " + i);
            }
        }
        long endChace = System.currentTimeMillis() - startCache;
        // Print the value
        System.out.println("endcache " + endChace);
//        for(int i = 0 ; i < 10000; i ++){
            long start = System.currentTimeMillis();
            for(int j = 0 ; j <  10000; j ++){
                cache.get("273910_D00" + ename(j));
            }
            long end = System.currentTimeMillis();
            System.out.println("use time <" + 0 + " - " + (10000) + ">, " + (end - start));
//        }

        Thread.sleep(100 * 1000);

    }

    private static String ename(int i) {
        if(i < 10){
            return "0000000" + i;
        }
        if(i < 100){
            return "000000" + i;
        }
        if(i < 1000){
            return "00000" + i;
        }
        if(i < 10000){
            return "0000" + i;
        }
        if(i < 100000){
            return "000" + i;
        }
        if(i < 1000000){
            return "00" + i;
        }
        if(i < 10000000){
            return "0" + i;
        }
        return "" + i;
    }



}
class SimpleEntity implements Serializable {
    private String productName;
    private Long groupId;
    private String entityName;

    public SimpleEntity() {
    }

    public SimpleEntity(String productName, Long groupId, String entityName) {
        this.productName = productName;
        this.groupId = groupId;
        this.entityName = entityName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String toString() {
        return "SimpleEntity{" +
                "productName='" + productName + '\'' +
                ", groupId=" + groupId +
                ", entityName='" + entityName + '\'' +
                '}';
    }
}