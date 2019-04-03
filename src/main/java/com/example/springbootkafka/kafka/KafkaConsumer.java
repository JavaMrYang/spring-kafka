package com.example.springbootkafka.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaConsumer {

    private Logger log= LoggerFactory.getLogger(KafkaConsumer.class);
    /**
  * 消费信息,监听一系列topic的信息
  * @param record
  */

    @KafkaListener(groupId = "0",topics = {"test","kafkatest"})
    private void consumerMessage(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("----------------- 返回的信息对象:{}", record);
            log.info("------------------收到的message:{}", message);
        }
    }
}
