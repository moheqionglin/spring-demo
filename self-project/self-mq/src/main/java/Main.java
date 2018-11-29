import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;

/**
 * @author wanli.zhou
 * @description
 * @time 10/10/2018 9:59 AM
 */
public class Main {
    public static void main(String[] args) {

        Broker broker = new Broker(10);

        final Producer producer = new Producer(broker);
        final Consumer consumer = new Consumer(broker);

        new Thread(){
            @Override
            public void run() {
                for(int i = 0 ; i < 100; i ++){
                    final HashMap<String, String> map = new HashMap<>();
                    map.put("header1", "value1");
                    producer.sendAsync(new Message(map, "消息" + i), new SendSuccessCallback() {
                        @Override
                        public void process(Message message, Object ctx) {
                            System.out.println("Send success message : " + message + ", ctx = " + ctx);
                        }
                    }, Arrays.asList(new String[]{"ctx-1", "ctx-2"}));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                for(;;){
                    HashMap<String, String> map = new HashMap<>();
                    map.put("header1", "value1");
                    System.out.println("consumer success : " + consumer.poll());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


    }
}