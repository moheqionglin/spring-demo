package limit.multiplehosts.redis.bzi;

import limit.multiplehosts.redis.aop.Limit;
import limit.multiplehosts.redis.aop.LimitType;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wanli.zhou
 * @description
 * @time 17/12/2018 10:06 AM
 */
@Component
public class OrderController {

    AtomicInteger count = new AtomicInteger(0);

    @Limit(moduleName = "order-service", apiName = "order.createOrder", period = 10, permitsMaxCount = 10, limitType = LimitType.CUSTOM_DEFINED)
    public void createOrder(String orderDetail) throws InterruptedException {
        System.out.println("> Start create order for " + orderDetail);
        Thread.sleep(100);
        System.out.println(">[" + count.incrementAndGet() + "] finish create order for " + orderDetail);
    }



}