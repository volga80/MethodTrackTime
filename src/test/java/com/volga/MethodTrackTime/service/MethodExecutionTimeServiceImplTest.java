package com.volga.MethodTrackTime.service;

import com.volga.MethodTrackTime.dao.MethodExecutionTimeRepository;
import com.volga.MethodTrackTime.domain.MethodExecutionTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MethodExecutionTimeServiceImplTest {

    @InjectMocks
    MethodExecutionTimeServiceImpl serviceImpl;

    @Mock
    MethodExecutionTimeRepository repository;

    @Test
    void save() {
        String className = "testClass";
        String methodName = "testMethod";
        long executionTime = 5L;
        MethodExecutionTime methodExecutionTime = new MethodExecutionTime(0,
                className, methodName, executionTime);

        serviceImpl.save(className, methodName, executionTime);

        verify(repository, times(1)).save(methodExecutionTime);
    }
}