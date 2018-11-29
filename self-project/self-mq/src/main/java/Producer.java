import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 10/10/2018 9:59 AM
 */
public class Producer {

    Broker broker = null;

    public Producer(Broker broker) {
        this.broker = broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public void send(Message message){
        synchronized (broker){
            while (broker.remaindSize() < 0){
                try {
                    broker.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            broker.getQueue().add(message);
            broker.getQueueSize().incrementAndGet();
            broker.notifyAll();
        }
    }

    public void sendAsync(Message message, SendSuccessCallback sendSuccessCallback, Object ctx){
        synchronized (broker){
            while (broker.remaindSize() < 0){
                try {
                    broker.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            broker.getQueue().add(message);
            broker.getQueueSize().incrementAndGet();
            broker.notifyAll();
            sendSuccessCallback.process(message, ctx);
        }
    }

}