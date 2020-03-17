package com.moheqionglin.tomcat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 16:14
 */
public class UserServlet extends HttpServlet {

    HashMap<String, User> userMap = new HashMap<>();

    public UserServlet(){
        userMap.put("zhangsan", new User("张三", "中国-北京", 1));
        userMap.put("lisi", new User("李四", "中国-上海", 2));
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String queryString = req.getQueryString();
        String first = Stream.of(queryString.split("&")).filter(s -> s.contains("name=")).findFirst().orElseGet(null);
        if(first != null){
            String name = first.split("=")[1];
            User user = userMap.get(name);
            String response = user == null ? "null" : user.toString();
            resp.getOutputStream().write(response.getBytes("utf-8"));
            resp.getOutputStream().flush();
        }
    }
}