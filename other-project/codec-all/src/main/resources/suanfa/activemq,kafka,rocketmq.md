JMS 
   JDK指定的类似于JDBC的规范， 统一规范的接口：
         ConnectionFactory -> Connect -> session
         Producer -> Consumer -> Destion
   JMS的邮箱通信机制两种：
        1、 point-to-point       使用 Javax.jms.Queue
            a、消息默认保存在kahadb $AMQ_HOME/data/kr-store/data
            b、 queue保证每条数据能够被reveicer到
            c、 producer发布消息到queue如果此时没有consumer连接这个queue的时候， 那么消息暂存，直到有consumer来。
            d、 一个消息通过 只能发给一个 消费者，如果这个消费者没有及时ack，那么再把这个消息给其他。 
        2、 publish/subscribe    使用 javax.jms.topic  
            a、消息默认不落地，无状态
            b、不保证发布者发布的消息，所有订阅这都能收到
            c、发布者发布消息，如果这时候topic没有consumer，那么消息丢失。
            d、 广播形式。
   JMS投递消息方式： 两种
        1、NON_PERSISTENT 
        2、PERSISTENT   jms重启不会丢失。   
   JMS 消息格式
        1、StreamMessage -- Java原始值的数据流
        2、MapMessage--一套名称-值对
        3、TextMessage--一个字符串对象
        4、ObjectMessage--一个序列化的 Java对象
        5、BytesMessage--一个未解释字节的数据流      
   确认消息方式
       1、AUTO_ACKNOWLEDGE(自动通知)
       2、CLIENT_ACKNOWLEDGE(客户端自行决定通知时机)
       3、DUPS_OK_ACKNOWLEDGE(延时//批量通知)      

ActiveMq： 
     HA方案： master-salver方式， master收到消息，给slaver传一份，salver接受成功以后，master在ack producer。
            consumer只能跟 master通信。


Kafka  https://www.jianshu.com/p/aa274f8fe00f
   HA 方案                          
      1. Broker 选一个 controller 
         get /kafka/controller
         临时节点 {"version":1,"brokerid":0,"timestamp":"1538117568529"}  
         
      2. 每个 topic的partition 选举一个 leader
        使用第一步选出来的Controller 然后通过RPC方式产生 partiton的leader ，防止每个ISR都忘zk上注册，那么partiton上千以后，zk会有压力。
        https://www.jianshu.com/p/c1d6725ebf86
        
     
         ls /kafka/brokers/ids
         [3, 1, 0]
         
          get /kafka/brokers/ids/3
         临时节点{"listener_security_protocol_map":{"PLAINTEXT":"PLAINTEXT"},"endpoints":["PLAINTEXT://172.16.219.4:9092"],"jmx_port":-1,"host":"172.16.219.4","timestamp":"1538117590224","port":9092,"version":4}

        get /kafka/brokers/topics/event-topic-yufa
        {"version":1,"partitions":{"12":[0,2],"8":[2,1],"4":[1,2],"11":[2,0],"9":[0,1],"13":[1,0],"5":[2,0],"10":[1,2],"6":[0,2],"1":[1,0],"14":[2,1],"0":[0,2],"2":[2,1],"7":[1,0],"3":[0,1]}}   

         get /kafka/brokers/topics/event-topic-yufa/partitions/0/state
         {"controller_epoch":3,"leader":0,"version":1,"leader_epoch":0,"isr":[0,2]}
        
        其中 ISR 是replicate集合的意思， event-topic-yufa 的0号partition有 两个复制集 0，2 。 其中 leader是 0
        
        如果有ISR集合汇总的broker宕机，使用 （微软的PacificA选举算法）https://www.colabug.com/225369.html 
     
     3.  保证送达（delivery guarantee）
         At most once 消息可能会丢，但绝不会重复传输
         At least once 消息绝不会丢，但可能会重复传输
         Exactly once 每条消息肯定会被传输一次且仅传输一次
         数据处理与commit的顺序，在很大程度上决定了消息从broker到consumer的delivery guarantee semantic。
         at most once
         如果读到消息就提交，则是at most once(至多一次)，因为即使处理失败，因为消息已提交，offset已指向下一个，处理失败的消息也不会再处理了。
         at least once
         如果处理完成功后再提交，则是at least once（至少一次），消息必须处理成功。如果消息处理完，但commit时出错，这会导致重复消费消息，因此要求消息处理者要保证幂等。
         Exactly once
         业务需要做事务，保证 Exactly Once 语义
         这里业务场景被区分为两个：
         
         幂等操作
         业务代码需要自身添加事务操作
        
   kafka 时间轮
     
   kafka日志清理策略， 和合并
     https://blog.csdn.net/u013332124/article/details/82793381
     
   Kafka水位
       [admin@locationxkafkatest-vbj01c-3 lx-tripNotify-topic-test-1]$ pwd
       /wzdata/kafkaData/lx-tripNotify-topic-test-1
       [admin@locationxkafkatest-vbj01c-3 lx-tripNotify-topic-test-1]$ ll
       total 12
       -rw-rw-r-- 1 admin admin 10485760 Dec 14 12:13 00000000000000000127.index
       -rw-rw-r-- 1 admin admin      973 Dec 21 16:41 00000000000000000127.log
       -rw-rw-r-- 1 admin admin       10 Dec 14 12:13 00000000000000000127.snapshot
       -rw-rw-r-- 1 admin admin 10485756 Dec 14 12:13 00000000000000000127.timeindex
       -rw-rw-r-- 1 admin admin       10 Dec 14 12:13 leader-epoch-checkpoint
      https://www.cnblogs.com/huxi2b/p/7453543.html  
     
     
        