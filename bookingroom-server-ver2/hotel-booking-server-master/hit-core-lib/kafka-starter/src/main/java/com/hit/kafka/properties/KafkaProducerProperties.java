package com.hit.kafka.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Getter
@Setter
@Primary
@Configuration
@ConfigurationProperties(
        prefix = "spring.kafka.producer"
)
public class KafkaProducerProperties extends KafkaProperties.Producer {

    private String defaultTopic;

    private int deliveryTimeout = 5000;

    private int requestTimeout = 3000;

    private int queueSize = 100;

}
