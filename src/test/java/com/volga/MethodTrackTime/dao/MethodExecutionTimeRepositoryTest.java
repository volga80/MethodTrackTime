package com.volga.MethodTrackTime.dao;

import com.volga.MethodTrackTime.domain.MethodExecutionTime;
import com.volga.MethodTrackTime.model.dto.MethodExecutionDTO;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class MethodExecutionTimeRepositoryTest {

    @Autowired
    private MethodExecutionTimeRepository repository;

    @BeforeEach
    void setUp(){
        MethodExecutionTime methodExecutionTime1 = new MethodExecutionTime(1, "className1", "methodName1", 100L);
        MethodExecutionTime methodExecutionTime2 = new MethodExecutionTime(2,"className1", "methodName2", 200L);
        MethodExecutionTime methodExecutionTime3 = new MethodExecutionTime(3,"className2", "methodName3", 100L);
        MethodExecutionTime methodExecutionTime4 = new MethodExecutionTime(4,"className2", "methodName3", 200L);
        repository.save(methodExecutionTime1);
        repository.save(methodExecutionTime2);
        repository.save(methodExecutionTime3);
        repository.save(methodExecutionTime4);
    }


    @Test
    void listExecutionTimeByClass() {
        List<Object[]> result = repository.getListExecutionTimeByClass("className1");

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void avgExecutionTimeByClass() {
        double result = repository.getAvgExecutionTimeByClass("className1");

        assertNotNull(result);
        assertEquals(150, result);
    }

    @Test
    void maxExecutionTimeByClass() {
        List<Object[]> result = repository.getMaxExecutionTimeByClass("className1");
        long max = 0;
        for (Object[] entry : result){
            max = (long)entry[0];
        }

        assertNotNull(result);
        assertEquals(200, max);
    }

    @Test
    void executionTimeByMethodName() {
        String methodName = "methodName3";
        List<MethodExecutionDTO> result = repository.getExecutionTimeByMethodName(methodName);

        assertNotNull(result);
        assertEquals(2, result.size());

        MethodExecutionDTO dto1 = result.get(0);
        MethodExecutionDTO dto2 = result.get(1);
        assertEquals(100L, dto1.getExecutionTime());
        assertEquals(200L, dto2.getExecutionTime());
        assertEquals(methodName, dto1.getMethodName());
        assertEquals(methodName, dto2.getMethodName());
    }

    @Test
    void avgExecutionTimeByMethodName() {
        String methodName = "methodName3";
        MethodExecutionDTO result = repository.getAvgExecutionTimeByMethodName(methodName);

        assertNotNull(result);
        assertEquals(150L, result.getAvgExecutionTime());
        assertEquals(methodName, result.getMethodName());
    }

    @Test
    void maxExecutionTimeByMethodName() {
        String methodName = "methodName3";
        MethodExecutionDTO result = repository.getMaxExecutionTimeByMethodName(methodName);

        assertNotNull(result);
        assertEquals(200L, result.getExecutionTime());
        assertEquals(methodName, result.getMethodName());
    }
}