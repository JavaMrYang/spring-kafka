package com.example.springbootkafka.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProduct {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Logger log= LoggerFactory.getLogger(KafkaProduct.class);
    @GetMapping("/index")
    public String index(){
        return "kafka";
    }

    /**

      * 发送消息到kafka对应的topic

      *  @param topic

      * @param message

      */

    @RequestMapping("/kafka/sendMessage")
    public String sendMessage(@RequestParam("topic")String topic, @RequestParam("message")String message) {
        try {
            ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, 0, System.currentTimeMillis(), "test", message);
            send.addCallback(new SuccessCallback() {
                @Override
                public void onSuccess(@Nullable Object obj) {
                    SendResult<String, String> sendResult = (SendResult) obj;
                    log.info("消息发送成功:{}", sendResult);
                }
            }, new FailureCallback() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.info("消息发送失败,case:{}", throwable);
                    //失败处理
                }
            });
            return "消息发送成功";
        } catch (Exception e) {
            return "消息发送失败";
        }
    }
}
