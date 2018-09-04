package com.moheqionglin.springjetty.controller;

import com.moheqionglin.springjetty.message.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World! Welcome to visit waylau.com!";
    }

    @RequestMapping("/hello/way")
    public User helloWay() {
        return new User("Way Lau", 30);
    }
}