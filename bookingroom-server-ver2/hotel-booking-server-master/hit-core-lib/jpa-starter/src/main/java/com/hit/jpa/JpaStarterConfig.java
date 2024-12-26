package com.hit.jpa;

import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
@ComponentScan({"com.hit.jpa"})
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class
})
public class JpaStarterConfig {
}
