package ratelimit.guravaTokenBucket;

/**
 * @author wanli.zhou
 * @description
 * @time 28/11/2018 10:27 AM
 */
public class TokenBucketMain {


    public static void main(String[] args) {
        createOrderWithQueue();
    }




    public static void createOrderWithQueue(){
         Controller noQueuecontroller = new Controller();
         Runnable noQueueRunnable = () -> {
            for(int i = 0;; i++){
                String user = Thread.currentThread().getName() + " | " + i;
                noQueuecontroller.createOrderSync(user);
                System.out.println(" ======" + user + "========");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(noQueueRunnable).start();
        new Thread(noQueueRunnable).start();
    }

    public static void createOrderWithoutQueue(){
         QueueController queuecontroller = new QueueController();
         Runnable queueRunnable = () -> {
            for(int i = 0; i < 20; i++){
                String user = Thread.currentThread().getName() + " | " + i;
                queuecontroller.createOrderAsync(user);
                System.out.println(" ======" + user + "========");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(queueRunnable).start();
        new Thread(queueRunnable).start();
    }
}