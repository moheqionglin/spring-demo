package ratelimit.guravaTokenBucket;

import com.google.common.util.concurrent.RateLimiter;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wanli.zhou
 * @description
 * @time 28/11/2018 10:28 AM
 */
public class QueueController {
    final RateLimiter rateLimiter = RateLimiter.create(1);

    final Queue<OrderRequest> orderQueue = new ArrayDeque<>();

    public QueueController(){
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            boolean hasToken = rateLimiter.tryAcquire(1000, TimeUnit.MICROSECONDS);
            if(hasToken){
                if(!orderQueue.isEmpty()){
                    createOrder(orderQueue.poll().toString());
                }
            }

        }, 0, 100, TimeUnit.MICROSECONDS);


    }

    public void createOrderAsync(String user){
        boolean hasToken = rateLimiter.tryAcquire(1000, TimeUnit.MICROSECONDS);

        String message = user + " 创建订单成功";
        if(hasToken){
            if(!orderQueue.isEmpty()){//有人排队
                //从队列中消费一个。
                createOrder(orderQueue.poll().toString());
                //新来的加入队列尾巴
                addToQueue(message);
                System.out.println(user + " 正在排队中..., 前面有 " + orderQueue.size() + " 人在等待...");
            }else{
                //直接消费
                createOrder(message);
            }
        }else{
            //新来的加入队列尾巴
            addToQueue(message);
            System.out.println(user + " 正在排队中..., 前面有 " + orderQueue.size() + " 人在等待...");
        }
    }

    private void createOrder(String message) {
        System.out.println(message);
    }

    private void addToQueue(String s) {
        orderQueue.add(new OrderRequest(s));
    }

    class OrderRequest{
        String order = "";

        public OrderRequest(String order) {
            this.order = order;
        }

        public String getOrder() {
            return order;
        }

        @Override
        public String toString() {
            return order;
        }
    }
}