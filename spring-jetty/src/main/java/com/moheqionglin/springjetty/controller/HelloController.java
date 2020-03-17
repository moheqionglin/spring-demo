package com.moheqionglin.springjetty.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World! Welcome to visit waylau.com!";
    }

    @RequestMapping(value = "/hello/way/{time}", method = RequestMethod.GET)
    public User helloWay(@PathVariable("time") long time) throws InterruptedException {
        Thread.sleep(time);
        return new User("Way Lau", 30);
    }
}