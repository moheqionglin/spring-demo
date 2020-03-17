package com.moheqionglin.tomcat;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 16:22
 */
@WebServlet(name = "bank", urlPatterns = {"/bank/*"})
public class BankServlet extends HttpServlet {

    HashMap<String, Bank> banks = new HashMap<>();

    public BankServlet() {
        banks.put("gonghang", new Bank("工行", "308898989485245"));
        banks.put("nonghang", new Bank("农行", "200898989485245"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String queryString = req.getQueryString();
        String first = Stream.of(queryString.split("&")).filter(s -> s.contains("name=")).findFirst().orElseGet(null);
        if(first != null){
            String name = first.split("=")[1];
            Bank bank = banks.get(name);
            String response = bank == null ? "null" : bank.toString();

            resp.getOutputStream().write(response.getBytes("utf-8"));
            resp.getOutputStream().flush();
        }

    }
}