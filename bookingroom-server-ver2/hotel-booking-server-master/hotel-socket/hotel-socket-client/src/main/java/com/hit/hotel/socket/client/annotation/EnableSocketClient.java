package com.hit.hotel.socket.client.annotation;

import com.hit.hotel.socket.client.config.SocketClientConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Import(SocketClientConfig.class)
public @interface EnableSocketClient {
}
