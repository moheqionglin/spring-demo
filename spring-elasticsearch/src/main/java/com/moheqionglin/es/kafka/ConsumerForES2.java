package com.moheqionglin.es.kafka;

import com.alibaba.fastjson.JSONObject;
import com.moheqionglin.es.dao.EsCommonDao;
import com.moheqionglin.es.service.EsService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-08-06 16:59
 */
@Component
public class ConsumerForES2 {
   private Logger logger = LoggerFactory.getLogger(this.getClass());


   private long sleepTime = -1;

   AtomicLong atomicLong = new AtomicLong(0);

   @Autowired
   private EsService esService;


   @KafkaListener(id = "consumer-es-2", topics = "topic-dev", containerFactory = "kafkaListenerContainerFactory")
   public void listen(ConsumerRecord<Long, JSONObject> record, Acknowledgment acknowledgment) {
       JSONObject data = record.value();
       data.put("id", atomicLong.addAndGet(2));
       data.put("data-type", "event");
       esService.batchCreateData2( data);
       acknowledgment.acknowledge();

   }

}