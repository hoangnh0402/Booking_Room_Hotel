package com.hit.api;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.hit.api", "com.hit.common"})
public class ApiStarterConfig {
}
