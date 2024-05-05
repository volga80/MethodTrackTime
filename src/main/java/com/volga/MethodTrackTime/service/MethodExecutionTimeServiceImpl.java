package com.volga.MethodTrackTime.service;

import com.volga.MethodTrackTime.dao.MethodExecutionTimeRepository;
import com.volga.MethodTrackTime.domain.MethodExecutionTime;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class MethodExecutionTimeServiceImpl implements MethodExecutionTimeService {
    private final MethodExecutionTimeRepository methodExecutionTimeRepository;

    @Async
    public void save(String className, String methodName, long executionTime) {
        log.info("Сохранение времени выполнения метода {}", methodName);
        MethodExecutionTime methodExecutionTime = MethodExecutionTime.builder().
                className(className).
                methodName(methodName).
                executionTime(executionTime).
                build();
        methodExecutionTimeRepository.save(methodExecutionTime);
    }
}
