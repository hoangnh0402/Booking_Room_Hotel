package com.hit.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hit.kafka.extension.ListenableFutureCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@ConditionalOnProperty(
        value = {"spring.kafka.producer.enabled"},
        havingValue = "true"
)
public abstract class KafkaPublisher {

    protected final KafkaTemplate<String, String> kafkaTemplate;

    protected ObjectMapper objectMapper;

    protected KafkaPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public <T> void pushAsync(T payload, String topic) {
        this.sendMessageAsync(payload, topic, null);
    }

    public <T> void pushAsync(T payload, String topic, ListenableFutureCallback<String> callback) {
        this.sendMessageAsync(payload, topic, callback);
    }

    public <T> boolean pushSync(T payload, String topic) {
        return this.sendMessageSync(payload, topic, new HashMap<>(), null);
    }

    public <T> boolean pushSync(T payload, String topic, Map<String, byte[]> headers, Integer partition) {
        return this.sendMessageSync(payload, topic, headers, partition);
    }

    private <T> void sendMessageAsync(T data, final String topic, final ListenableFutureCallback<String> callback) {
        final String message;
        try {
            message = this.objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException var6) {
            log.error("Exception when parse data to json {}", var6.getMessage());
            return;
        }

        CompletableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topic, message);
        if (callback != null) {
            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    KafkaPublisher.log.info("xxxx> Unable to send message=[ {} ] to topic: {} FAIL !!! \n Reason: {}"
                            , message, topic, ex.getMessage(), ex);
                    callback.onFailure(ex);
                } else {
                    KafkaPublisher.log.info("===> Sent message=[ {} ] with offset=[ {} ] to topic: {} SUCCESS !!!",
                            message, result.getRecordMetadata().offset(), topic);
                    callback.onSuccess(message);
                }
            });
        }
    }

    private <T> boolean sendMessageSync(T data, final String topic, Map<String, byte[]> headers, Integer partition) {
        final String message;
        try {
            message = this.objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException var10) {
            log.error("Exception when parse data to json {}", var10.getMessage());
            return false;
        }

        ProducerRecord<String, String> rc;
        if (partition == null) {
            rc = new ProducerRecord<>(topic, message);
        } else {
            rc = new ProducerRecord<>(topic, partition, "", message);
        }

        if (!headers.isEmpty()) {
            for (Entry<String, byte[]> item : headers.entrySet()) {
                rc.headers().add(new RecordHeader(item.getKey(), item.getValue()));
            }
        }

        CompletableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(rc);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                KafkaPublisher.log.info("===> Sent message=[ {} ] with offset=[ {} ] to topic: {} SUCCESS !!!", message, result.getRecordMetadata().offset(), topic);
            } else {
                KafkaPublisher.log.info("xxxx> Unable to send message=[ {} ] to topic: {} FAIL !!! \n Reason: {}", message, topic, ex.getMessage(), ex);
            }
        });

        try {
            future.get();
            return true;
        } catch (InterruptedException | ExecutionException var9) {
            log.error("sendMessageSync exception: {}", var9.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
