package limit.singlehost.guava;


import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @author wanli.zhou
 * @description
 * @time 28/11/2018 4:36 PM
 */
public class TokenRateBucketTest {
    public static void main(String[] args) throws InterruptedException {
        testSoomthWarm();
    }

    /**
     *
     * guava 的 rateBucket算法， 不会记录最后一次 acquire 的 时间点。 而是记录最后一次 acquire应该 什么时候执行。
     * 举个例子：
     *    1QPS的 限速规则 要求 1个/S
     *
     *    Thread-1 acquire(10)
     *    Thread-2 acquire(1)
     *
     *    那么线程1 会通过限流程序执行。 但是因为线程1 透支了未来10S的流量，所以 线程2要想执行的话必须等待10S， 也就是使用第11S的资源。
     *
     */
    public static void test1(){
        RateLimiter rateLimiter = RateLimiter.create(1);

        System.out.println(rateLimiter.acquire(5));

        System.out.println(rateLimiter.acquire());
    }

    /**
     *
     * @throws InterruptedException
     * 囤货
     *
     *
     */

    public static void test2() throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(1);

        System.out.println(rateLimiter.acquire(1));
        Thread.sleep(6000);

        System.out.println(rateLimiter.acquire(1));

        System.out.println(rateLimiter.acquire(1));

        System.out.println(rateLimiter.acquire(1));

        System.out.println(rateLimiter.acquire(1));

    }


    /**
     *
     * 平滑预热
     *   平滑突发限流有可能瞬间带来了很大的流量，如果系统扛不住的话，很容易造成系统挂掉
     */

    public static void testSoomthWarm() throws InterruptedException {
        RateLimiter limiter = RateLimiter.create(5, 1000, TimeUnit.MILLISECONDS);

        for (int i = 1; i < 5; i++) {
            System.out.println(limiter.acquire());
        }
        Thread.sleep(1000L);
        for (int i = 1; i < 50; i++) {
            System.out.println(limiter.acquire());
        }

    }

}