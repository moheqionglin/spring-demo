package com.moheqiongli.mybits;

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
 * @time 2019-07-30 14:59
 */
public class SecondLevelCache {

    public static void main(String[] args) throws IOException, InterruptedException {
        String resource = "mybits-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        Runnable runnable = () -> {
            try (SqlSession session = sqlSessionFactory.openSession()) {
                Blog blog1 = session.selectOne("mapper.BlogMapper.selectBlog", 1);
                System.out.println(Thread.currentThread().getId() + " - " + blog1);
//            session.update("mapper.BlogMapper.updateBlog", new Blog(1L, "t1",  "c1-" + Thread.currentThread().getId()));
                Blog blog2 = session.selectOne("mapper.BlogMapper.selectBlog", 1);
                System.out.println(Thread.currentThread().getId() + " - " + blog2);

                Thread.sleep(10000);
                Blog blog3 = session.selectOne("mapper.BlogMapper.selectBlog", 1);
                System.out.println(Thread.currentThread().getId() + " - " + blog3);

                System.out.println("----");
                Blog blog5 = session.selectOne("mapper.BlogMapper.selectBlogWithoutCache", 1);
                System.out.println(Thread.currentThread().getId() + " - " + blog5);

                session.clearCache();
                Blog blog4 = session.selectOne("mapper.BlogMapper.selectBlog", 1);
                System.out.println(Thread.currentThread().getId() + " - " + blog4);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(runnable).start();
        Thread.sleep(5000);
        new Thread(runnable).start();
    }
}