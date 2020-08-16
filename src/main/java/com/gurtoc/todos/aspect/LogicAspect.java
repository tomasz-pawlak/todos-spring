package com.gurtoc.todos.aspect;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogicAspect {
    private final Timer projectCreateGroupTimer;
    private static final Logger LOGGER = LoggerFactory.getLogger(LogicAspect.class);

    public LogicAspect(final MeterRegistry registry) {
        projectCreateGroupTimer = registry.timer("logic.project.create.group");
    }

    @Pointcut("execution(* com.gurtoc.todos.logic.ProjectService.createGroup(..))")
    static void projectServiceCreateGroup(){

    }

    @Before("projectServiceCreateGroup()")
    void logMethodCall(JoinPoint point){
        LOGGER.info("Before {} with {}", point.getSignature().getName(), point.getArgs());
    }

    @Around("projectServiceCreateGroup()")//laczy sie z jaka metoda, dookoła
    Object aroundProjectCreateGroup(ProceedingJoinPoint point){
        return projectCreateGroupTimer.record(()->{
            try {
                return point.proceed();
            } catch (Throwable e) {
               if (e instanceof RuntimeException){
                   throw (RuntimeException) e;
               }
               throw new RuntimeException(e);
            }
        });

    }


}
