package com.moheqiongli.mybits.onlymybatis;


import com.moheqiongli.mybits.domain.Blog;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-30 13:38
 * http://www.mybatis.org/mybatis-3/zh/getting-started.html
 *
 * 先 二级缓存->在一级缓存->数据库
 */

public class FirstLevelCache {
    public static void main(String[] args) throws IOException {
        String resource = "only-mybits/mapper/mybits-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            Blog blog1 = session.selectOne("mapper.BlogMapper.selectBlog", 1);
            System.out.println(blog1);
//            session.update("mapper.BlogMapper.updateBlog", new Blog(1L, "t1",  "c1-" + Thread.currentThread().getId()));
//            Blog blog2 = session.selectOne("mapper.BlogMapper.selectBlog", 1);
//            System.out.println(blog2);

            //二级缓存 readonly ， 那么返回的cache对象就一个， 一旦修改这个对象，后面在查询的时候，cache对象就会改变。
            blog1.setContent("xxx");
            Thread.sleep(4000);
            Blog blog3 = session.selectOne("mapper.BlogMapper.selectBlog", 1);
            System.out.println(blog3);

            //由于数据库的事务隔离级别，所以这里看到的还是 c1-update12
            System.out.println("----");
            Blog blog5 = session.selectOne("mapper.BlogMapper.selectBlogWithoutCache", 1);
            System.out.println(blog5);



//
//            session.clearCache();
//            Blog blog4 = session.selectOne("mapper.BlogMapper.selectBlog", 1);
//            System.out.println(blog4);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}