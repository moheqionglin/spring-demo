package limit.singlehost.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @author wanli.zhou
 * @description
 * @time 28/11/2018 10:28 AM
 */
public class Controller {
    final RateLimiter rateLimiter = RateLimiter.create(1);

    public void createOrderSync(String user){
        double acquireTime = rateLimiter.acquire();
        System.out.println(user + " wait time " + acquireTime + ", create order. ");
    }

    public void createOrderAsync(String user){
        boolean hasToken = rateLimiter.tryAcquire(1000, TimeUnit.MICROSECONDS);
        if(hasToken){
            System.out.println(user + " 创建订单成功");
            return;
        }
        System.out.println(user + " 创建订单失败");
    }
}