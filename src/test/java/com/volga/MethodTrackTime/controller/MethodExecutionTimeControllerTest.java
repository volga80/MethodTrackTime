package com.volga.MethodTrackTime.controller;

import com.volga.MethodTrackTime.dao.MethodExecutionTimeRepository;
import com.volga.MethodTrackTime.model.dto.MethodExecutionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MethodExecutionTimeControllerTest {

    @InjectMocks
    MethodExecutionTimeController controller;

    @Mock
    MethodExecutionTimeRepository repository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listOfTimesByClass() throws Exception {
        String className = "classTest";
        List<Object[]> executionTimes = new ArrayList<>();
        Object[] execution1 = new Object[]{"methodName1", 100L};
        Object[] execution2 = new Object[]{"methodName2", 200L};
        executionTimes.add(execution1);
        executionTimes.add(execution2);

        when(repository.getListExecutionTimeByClass(className)).thenReturn(executionTimes);

        mockMvc.perform(get("/statistics/timesByClass")
                        .param("className", className)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0][0]").value("methodName1"))
                .andExpect(jsonPath("$[0][1]").value(100));
        verify(repository, times(1)).getListExecutionTimeByClass(className);
    }

    @Test
    void avgTimeByClass() throws Exception {
        String className = "classTest";
        Double avgTime = 100D;

        when(repository.getAvgExecutionTimeByClass(className)).thenReturn(avgTime);

        mockMvc.perform(get("/statistics/avgTimeByClass")
                        .param("className", className)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(100D));
        verify(repository, times(1)).getAvgExecutionTimeByClass(className);
    }

    @Test
    void maxTimeByClassAndMethod() throws Exception {
        String className = "classTest";
        List<Object[]> maxExecutionTimes = new ArrayList<>();
        Object[] execution1 = new Object[]{"methodName1", 200L};
        Object[] execution2 = new Object[]{"methodName2", 200L};
        maxExecutionTimes.add(execution1);
        maxExecutionTimes.add(execution2);

        when(repository.getMaxExecutionTimeByClass(className)).thenReturn(maxExecutionTimes);

        mockMvc.perform(get("/statistics/maxTimeByClass")
                        .param("className", className)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0][0]").value("methodName1"))
                .andExpect(jsonPath("$[0][1]").value(200));
        verify(repository, times(1)).getMaxExecutionTimeByClass(className);
    }

    @Test
    void executionTimeByMethodName() throws Exception {
        String methodName = "testClass";
        List<MethodExecutionDTO> executionTimes = new ArrayList<>();
        MethodExecutionDTO dto1 = new MethodExecutionDTO(100L, "methodName1"
                , null);
        MethodExecutionDTO dto2 = new MethodExecutionDTO(200L, "methodName2"
                , null);
        executionTimes.add(dto1);
        executionTimes.add(dto2);

        when(repository.getExecutionTimeByMethodName(methodName)).thenReturn(executionTimes);

        mockMvc.perform(get("/statistics/executionTimeByMethod")
                        .param("methodName", methodName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].executionTime").value(100))
                .andExpect(jsonPath("$[0].methodName").value("methodName1"));
    }

    @Test
    void avgExecutionTimeByMethodName() throws Exception {
        String methodName = "testMethod";
        MethodExecutionDTO dto = new MethodExecutionDTO(null, "method",
                22.2);

        when(repository.getAvgExecutionTimeByMethodName(methodName)).thenReturn(dto);

        mockMvc.perform(get("/statistics/avgExecutionTimeByMethod")
                        .param("methodName", methodName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.avgExecutionTime").value(22.2))
                .andExpect(jsonPath("$.methodName").value("method"));
    }

    @Test
    void maxExecutionTimeByMethodName() throws Exception {
        String methodName = "testMethod";
        MethodExecutionDTO dto = new MethodExecutionDTO(200L, "method",
                null);

        when(repository.getMaxExecutionTimeByMethodName(methodName)).thenReturn(dto);

        mockMvc.perform(get("/statistics/maxExecutionTimeByMethod")
                        .param("methodName", methodName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.executionTime").value(200))
                .andExpect(jsonPath("$.methodName").value("method"));
    }
}