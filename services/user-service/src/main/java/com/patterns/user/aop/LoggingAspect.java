package com.patterns.user.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.patterns.user.service.UserService.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        System.out.println("Executing method: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.patterns.user.service.UserService.*(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("Method executed: " + joinPoint.getSignature().getName() + ", Result: " + result);
    }
}