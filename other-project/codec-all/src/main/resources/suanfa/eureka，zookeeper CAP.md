https://blog.csdn.net/paincupid/article/details/80610441

Eureka: AP 
   哲学：  有数据就是最好的数据，   如果eureka急群众，一个eureka 因为网络分区失去心跳， 那么 他会进入保护模式， 这个时候，丢失心跳的eureka的数据不会被移除，然后还可以继续接受新的服务注册。
   
   A保证： 
            1、 eureka 集群某个机器网络心跳丢失，那么进入保护模式。
            2、 eureka client的缓存模式， 即使eureka全挂了， 用缓存照样可以工作。
            
            
Zookeeper: CP
     
     C保证：            