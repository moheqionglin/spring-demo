/**
 * @author wanli.zhou
 * @description
 * @time 10/10/2018 10:09 AM
 */
public class Consumer {
    Broker broker;

    public Consumer(Broker broker) {
        this.broker = broker;
    }

    public Message poll(){
        synchronized (broker){
            while (broker.getQueueSize().get() < broker.getMinSize()){
                try {
                    broker.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Message m = broker.getQueue().poll();
            broker.getQueueSize().decrementAndGet();
            broker.notifyAll();
           return m;
        }
    }
}