package com.hit.hotel.repository;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Aspect
@Configuration
public class RepositoryAspect {

    @Value("${app.repository.query-limit-warning-ms:60}")
    private int executionLimitMs;

    @Around("execution(* com.hit.hotel.repository.*.repository.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        String message = joinPoint.getSignature() + " exec in " + executionTime + " ms";
        if (executionTime >= executionLimitMs) {
            log.warn(message + " : SLOW QUERY");
        } else {
            log.debug(message);
        }
        return proceed;
    }
}