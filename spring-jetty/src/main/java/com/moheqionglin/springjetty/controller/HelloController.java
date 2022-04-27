package com.moheqionglin.springjetty.controller;

import com.alibaba.fastjson.JSONObject;
import com.moheqionglin.springjetty.AES对称Test;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;

@RestController
@DefaultProperties(defaultFallback = "fail1")
public class HelloController {

    @HystrixCommand(fallbackMethod = "fail1",
            commandProperties = {
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "100" ),
//                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "20000" ),
//                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_FORCE_OPEN, value = "false" ),
//                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "3"),
//                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_FORCE_CLOSED, value = "false" ),
//                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "50" )
            }
//            threadPoolKey = "wanli-hystrix",
//            threadPoolProperties = {
//                    @HystrixProperty(name = HystrixPropertiesManager.CORE_SIZE, value = "3" )
//            }
//                    // 熔断器在整个统计时间内是否开启的阀值
//                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
//                    // 至少有3个请求才进行熔断错误比率计算
//                    /**
//                     * 设置在一个滚动窗口中，打开断路器的最少请求数。
//                     比如：如果值是20，在一个窗口内（比如10秒），收到19个请求，即使这19个请求都失败了，断路器也不会打开。
//                     */
//                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
//                    //当出错率超过50%后熔断器启动
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
//                    // 熔断器工作时间，超过这个时间，先放一个请求进去，成功的话就关闭熔断，失败就再等一段时间
//                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "500"),
//                    /**
//                     * 设置存活时间，单位分钟。如果coreSize小于maximumSize，那么该属性控制一个线程从实用完成到被释放的时间.
//                     */
//                    @HystrixProperty(name = "coreSize", value = "30"),
//                    /**
//                     * BlockingQueue的最大队列数，当设为-1，会使用SynchronousQueue，值为正时使用LinkedBlcokingQueue。
//                     */
//                    @HystrixProperty(name = "maxQueueSize", value = "101"),
///**
// 我们知道，线程池内核心线程数目都在忙碌，再有新的请求到达时，线程池容量可以被扩充为到最大数量。
// 等到线程池空闲后，多于核心数量的线程还会被回收，此值指定了线程被回收前的存活时间，默认为 2，即两分钟。
// */
//                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
//                    /**
//                     * 设置队列拒绝的阈值,即使maxQueueSize还没有达到
//                     */
//                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
//
//// 滑动统计的桶数量
//                    /**
//                     * 设置一个rolling window被划分的数量，若numBuckets＝10，rolling window＝10000，
//                     *那么一个bucket的时间即1秒。必须符合rolling window % numberBuckets == 0。默认1
//                     */
//                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"),
//                    // 设置滑动窗口的统计时间。熔断器使用这个时间
//                    /** 设置统计的时间窗口值的，毫秒值。
//                     circuit break 的打开会根据1个rolling window的统计来计算。
//                     若rolling window被设为10000毫秒，则rolling window会被分成n个buckets，
//                     每个bucket包含success，failure，timeout，rejection的次数的统计信息。默认10000
//                     **/
//                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
//            }
    )
    @RequestMapping("/hello")
    public String hello(@RequestParam("id") Integer id) throws InterruptedException {
//
        if (id % 2 == 0) {
            Thread.sleep(300);
        }
        String s = "Hello World! Welcome to visit waylau.com!" + System.currentTimeMillis();
        System.out.println(s);
        return s;
    }

    @HystrixCommand(fallbackMethod = "fail1",
            commandProperties = {
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "100" ),
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "20000" ),
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_FORCE_OPEN, value = "false" ),
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "3"),
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_FORCE_CLOSED, value = "false" ),
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "50" )
            }
//            threadPoolKey = "wanli-hystrix",
//            threadPoolProperties = {
//                    @HystrixProperty(name = HystrixPropertiesManager.CORE_SIZE, value = "3" )
//            }
//                    // 熔断器在整个统计时间内是否开启的阀值
//                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
//                    // 至少有3个请求才进行熔断错误比率计算
//                    /**
//                     * 设置在一个滚动窗口中，打开断路器的最少请求数。
//                     比如：如果值是20，在一个窗口内（比如10秒），收到19个请求，即使这19个请求都失败了，断路器也不会打开。
//                     */
//                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
//                    //当出错率超过50%后熔断器启动
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
//                    // 熔断器工作时间，超过这个时间，先放一个请求进去，成功的话就关闭熔断，失败就再等一段时间
//                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "500"),
//                    /**
//                     * 设置存活时间，单位分钟。如果coreSize小于maximumSize，那么该属性控制一个线程从实用完成到被释放的时间.
//                     */
//                    @HystrixProperty(name = "coreSize", value = "30"),
//                    /**
//                     * BlockingQueue的最大队列数，当设为-1，会使用SynchronousQueue，值为正时使用LinkedBlcokingQueue。
//                     */
//                    @HystrixProperty(name = "maxQueueSize", value = "101"),
///**
// 我们知道，线程池内核心线程数目都在忙碌，再有新的请求到达时，线程池容量可以被扩充为到最大数量。
// 等到线程池空闲后，多于核心数量的线程还会被回收，此值指定了线程被回收前的存活时间，默认为 2，即两分钟。
// */
//                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
//                    /**
//                     * 设置队列拒绝的阈值,即使maxQueueSize还没有达到
//                     */
//                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
//
//// 滑动统计的桶数量
//                    /**
//                     * 设置一个rolling window被划分的数量，若numBuckets＝10，rolling window＝10000，
//                     *那么一个bucket的时间即1秒。必须符合rolling window % numberBuckets == 0。默认1
//                     */
//                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"),
//                    // 设置滑动窗口的统计时间。熔断器使用这个时间
//                    /** 设置统计的时间窗口值的，毫秒值。
//                     circuit break 的打开会根据1个rolling window的统计来计算。
//                     若rolling window被设为10000毫秒，则rolling window会被分成n个buckets，
//                     每个bucket包含success，failure，timeout，rejection的次数的统计信息。默认10000
//                     **/
//                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
//            }
    )
    @RequestMapping("/hello2")
    public String hello2(@RequestParam("id") Integer id) throws InterruptedException {
//
        return "Hello World! Welcome to visit waylau.com!" + System.currentTimeMillis();
    }
    @RequestMapping(value = "/hello/way/{time}", method = RequestMethod.GET)
    public User helloWay(@PathVariable("time") long time) throws InterruptedException {
        Thread.sleep(time);
        return new User("Way Lau", 30);
    }

    @RequestMapping(value = "/hello/post", method=RequestMethod.POST)
    public User helloPost(@RequestBody User user, @RequestHeader("privacy-enable") boolean privateEnable) throws InterruptedException{
        user.setPrivacyEnable(privateEnable);
        return user;
    }
    @RequestMapping(value = "/hello/encrypt/post", method=RequestMethod.POST)
    public User helloEncryptPost(@RequestBody String encrypt, @RequestHeader("privacy-enable") boolean privateEnable, @RequestHeader("privacy-header-param") String headerEncrypt) throws Exception {
        String s = AES对称Test.gcmDecrypt(encrypt);
        User user = JSONObject.parseObject(s, User.class);
        user.setMethod("post");
        user.setPrivacyEnable(privateEnable);
        user.setHeaderPlainText(AES对称Test.gcmDecrypt(headerEncrypt));
        return user;
    }
    @RequestMapping(value = "/hello/encrypt/get", method=RequestMethod.GET)
    public User helloEncryptGet(@RequestParam String privacyParam, @RequestHeader("privacy-enable") boolean privateEnable, @RequestHeader("privacy-header-param") String headerEncrypt) throws Exception {
        String s = AES对称Test.gcmDecrypt(privacyParam);
        HashMap<String,String> map = new HashMap();
        Arrays.stream(s.split("&")).forEach(l -> {
            System.out.println("--->" + l);
            String[] split = l.split("=");
            map.put(split[0],split[1]);
        });
        System.out.println("---->>>" + map);
        User user = new User();
        user.setUsername(map.get("username"));
        user.setAge(Integer.valueOf(map.get("age")));
        user.setMethod("get");
        user.setHeaderPlainText(AES对称Test.gcmDecrypt(headerEncrypt));
        user.setPrivacyEnable(privateEnable);
        return user;
    }
    private String fail1( Integer id) {

        String v ="fail1" + System.currentTimeMillis();
        System.out.println(v);
        return v;
    }
}