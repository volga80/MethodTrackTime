package com.volga.MethodTrackTime.aspect;

import com.volga.MethodTrackTime.service.MethodExecutionTimeService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@Slf4j
public class MethodExecutionAspect {

    @Autowired
    private MethodExecutionTimeService MethodExecutionTimeService;

    @Pointcut("within(com.volga.MethodTrackTime.service.*Service)")
    public void isService() {
    }

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isClassWithAnnotationService() {
    }

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Async)")
    public void hasTrackAsyncTimeAnnotation() {
    }

    @Pointcut("@annotation(com.volga.MethodTrackTime.annotation.TrackTime)")
    public void hasTrackTimeAnnotation() {
    }

    @Around("isClassWithAnnotationService() && isService()  && hasTrackTimeAnnotation()")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Запущен метод {}", joinPoint.getSignature().getName());
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        MethodExecutionTimeService.save(joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().toShortString(), executionTime);
        log.info("Завершил работу метод {}, время работы метода {}ms", joinPoint.getSignature().getName(), executionTime);
        return result;
    }

    @Around("isClassWithAnnotationService() && isService() && hasTrackAsyncTimeAnnotation()")
    public Object trackAsyncTime(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Запущен асинхронно метод {}", joinPoint.getSignature().getName());
        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            if (result instanceof CompletableFuture) {
                CompletableFuture<Object> future = (CompletableFuture<Object>) result;
                return future.thenApplyAsync(res -> {
                    long endTime = System.currentTimeMillis();
                    long executionTime = endTime - startTime;
                    log.info("Завершил работу асинхронно метод {}, время выполнения {}ms",
                            joinPoint.getSignature().getName(),
                            executionTime);
                    MethodExecutionTimeService.save(joinPoint.getTarget().getClass().getSimpleName(),
                            joinPoint.getSignature().toShortString(), executionTime);
                    return res;
                });
            } else {
                throw new IllegalArgumentException("Метод с аннотацией @TrackAsync " +
                        "должен вернуть CompletableFuture");
            }
        } catch (Throwable throwable) {
            log.error("Ошибка при выполнении асинхронного метода", throwable);
            throw throwable;
        }
    }
}
