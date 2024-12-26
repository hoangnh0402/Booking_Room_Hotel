package com.hit.kafka.properties;

import lombok.Getter;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Getter
@Primary
@Configuration
@ConfigurationProperties(
        prefix = "spring.kafka.consumer"
)
public class KafkaConsumerProperties extends KafkaProperties.Consumer {
}
