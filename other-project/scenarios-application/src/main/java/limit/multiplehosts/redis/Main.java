package limit.multiplehosts.redis;

import limit.multiplehosts.redis.bzi.OrderController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 17/12/2018 2:56 PM
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

        OrderController orderController = (OrderController) applicationContext.getBean("orderController");

        for(int i = 0 ; ; i++){
            Thread.sleep(800);
            try{
                orderController.createOrder("Order - " + i);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}