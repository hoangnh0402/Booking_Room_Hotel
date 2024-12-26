package com.hit.hotel.socket.server.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
public class SocketIOAnnotationScanner implements BeanPostProcessor {

    private final List<Class<? extends Annotation>> annotations;

    private final SocketIOServer socketIOServer;

    private Class<?> originalBeanClass;
    private Object originalBean;
    private String originalBeanName;

    public SocketIOAnnotationScanner(@Lazy SocketIOServer socketIOServer) {
        this.annotations = Arrays.asList(OnConnect.class, OnDisconnect.class, OnEvent.class);
        this.socketIOServer = socketIOServer;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        final AtomicBoolean add = new AtomicBoolean();
        ReflectionUtils.doWithMethods(bean.getClass(), methodCallback -> add.set(true), methodFilter -> {
            Iterator<Class<? extends Annotation>> annotations = SocketIOAnnotationScanner.this.annotations.iterator();

            Class<? extends Annotation> annotationClass;
            do {
                if (!annotations.hasNext()) {
                    return false;
                }

                annotationClass = annotations.next();
            } while(!methodFilter.isAnnotationPresent(annotationClass));

            return true;
        });
        if (add.get()) {
            this.originalBeanClass = bean.getClass();
            this.originalBean = bean;
            this.originalBeanName = beanName;
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (this.originalBeanClass != null) {
            this.socketIOServer.addListeners(this.originalBean, this.originalBeanClass);
            log.info("{} bean listeners added", this.originalBeanName);
            this.originalBeanClass = null;
            this.originalBeanName = null;
        }
        return bean;
    }
}