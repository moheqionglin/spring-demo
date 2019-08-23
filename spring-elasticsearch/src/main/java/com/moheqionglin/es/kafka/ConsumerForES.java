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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-08-06 16:59
 */
@Component
public class ConsumerForES {
   private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EsService esService;

    AtomicLong atomicLong = new AtomicLong(1);


   @KafkaListener(id = "consumer-es-1", topics = "topic-dev1", containerFactory = "kafkaListenerContainerFactory")
   public void listen(ConsumerRecord<Long, JSONObject> record, Acknowledgment acknowledgment) {
       JSONObject data = record.value();
       data.put("id", atomicLong.addAndGet(2));
       data.put("data-type", "point");
       data.put("location",  data.get("lat") +" , " + data.get("lon"));
       esService.batchCreateData1(data);
       acknowledgment.acknowledge();

   }

}