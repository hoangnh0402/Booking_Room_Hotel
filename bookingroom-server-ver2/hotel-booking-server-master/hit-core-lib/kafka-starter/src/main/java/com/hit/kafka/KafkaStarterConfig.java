package com.hit.kafka;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = {
        KafkaAutoConfiguration.class
})
@ComponentScan({"com.hit.kafka"})
public class KafkaStarterConfig {
}
