import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wanli.zhou
 * @description
 * @time 10/10/2018 10:01 AM
 */
public class Broker {
    private Queue<Message> queue = new LinkedList<>();
    private int maxSize;
    private AtomicInteger queueSize = new AtomicInteger();

    public AtomicInteger getQueueSize() {
        return queueSize;
    }

    public Broker(int maxSize) {
        this.maxSize = maxSize;
    }

    public Queue<Message> getQueue() {
        return queue;
    }

    public void setQueue(Queue<Message> queue) {
        this.queue = queue;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int remaindSize(){
        return maxSize - queueSize.get();
    }
}