package com.hit.kafka.config;

import com.hit.kafka.properties.KafkaProducerProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Getter
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(
        value = {"spring.kafka.producer.enabled"},
        havingValue = "true"
)
public class KafkaProducerConfig {

    private final KafkaProducerProperties kafkaProducerProperties;

    @Bean("kafkaEventProducerFactory")
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", this.kafkaProducerProperties.getBootstrapServers());
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", StringSerializer.class);
        props.put("retries", this.kafkaProducerProperties.getRetries());
        props.put("delivery.timeout.ms", this.kafkaProducerProperties.getDeliveryTimeout());
        props.put("request.timeout.ms", this.kafkaProducerProperties.getRequestTimeout());
        return new DefaultKafkaProducerFactory<>(props, new StringSerializer(), new StringSerializer());
    }

    @Bean("defaultKafkaTemplate")
    @Primary
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(this.producerFactory());
    }
}

