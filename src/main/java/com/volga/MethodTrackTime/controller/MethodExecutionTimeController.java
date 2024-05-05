package com.volga.MethodTrackTime.controller;

import com.volga.MethodTrackTime.dao.MethodExecutionTimeRepository;
import com.volga.MethodTrackTime.model.dto.MethodExecutionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/statistics")
@Tag(name = "MethodExecutionTimeController", description = "Контроллер для " +
        "получения статистики по времени работы методов")
public class MethodExecutionTimeController {

    private final MethodExecutionTimeRepository methodExecutionTimeRepository;

    @Operation(
            summary = "Список затраченного времени на методы класса",
            description = "Принимает название класса и возвращает список затраченного времени " +
                    "на методы заданного класса")
    @GetMapping("/timesByClass")
    public ResponseEntity<List<Object[]>> getListTimesByClass(
            @RequestParam("className") @Parameter(description = "Имя класса") String className) {
        List<Object[]> executionTimes = methodExecutionTimeRepository.getListExecutionTimeByClass(className);
        return ResponseEntity.ok(executionTimes);
    }

    @Operation(
            summary = "Среднее время затраченное на методы класса",
            description = "Принимает название класса и возвращает среднее затраченное время " +
                    "в этом классе")
    @GetMapping("/avgTimeByClass")
    public ResponseEntity<Double> getAvgTimeByClass(
            @RequestParam("className") @Parameter(description = "Имя класса") String className) {
        Double avgExecutionTime = methodExecutionTimeRepository.getAvgExecutionTimeByClass(className);
        return ResponseEntity.ok(avgExecutionTime);
    }

    @Operation(
            summary = "Максимальное время затраченное в классе и название метода",
            description = "Принимает название класса и возвращает максимальное затраченное время " +
                    "в этом классе, с названием метода")
    @GetMapping("/maxTimeByClass")
    public ResponseEntity<List<Object[]>> getMaxTimeByClassAndMethod(
            @RequestParam("className") @Parameter(description = "Имя класса") String className) {
        List<Object[]> maxExecutionTime = methodExecutionTimeRepository.getMaxExecutionTimeByClass(className);
        return ResponseEntity.ok(maxExecutionTime);
    }

    @Operation(
            summary = "Список затраченного времени на определенный метод",
            description = "Принимает название метода с указанием класса и возвращает " +
                    "список затраченного на этот метод времени, с использованием DTO")
    @GetMapping("/executionTimeByMethod")
    public ResponseEntity<List<MethodExecutionDTO>> getExecutionTimeByMethodName(
            @RequestParam("methodName") @Parameter(description = "Имя метода") String methodName) {
        List<MethodExecutionDTO> executionTimes = methodExecutionTimeRepository
                .getExecutionTimeByMethodName(methodName);
        return ResponseEntity.ok(executionTimes);
    }

    @Operation(
            summary = "Среднее время затраченное на определенный метод",
            description = "Принимает название метода с указанием класса и возвращает " +
                    "среднее затраченное время на этот метод с его названием, с использованием DTO")
    @GetMapping("/avgExecutionTimeByMethod")
    public ResponseEntity<MethodExecutionDTO> getAvgExecutionTimeByMethodName(
            @RequestParam("methodName") @Parameter(description = "Имя метода") String methodName) {
        MethodExecutionDTO avgExecution = methodExecutionTimeRepository
                .getAvgExecutionTimeByMethodName(methodName);
        return ResponseEntity.ok(avgExecution);
    }

    @Operation(
            summary = "Максимальное время затраченное на определенный метод",
            description = "Принимает название метода с указанием класса и возвращает " +
                    "максимально затраченное время на этот метод с его названием, с использованием DTO")
    @GetMapping("/maxExecutionTimeByMethod")
    public ResponseEntity<MethodExecutionDTO> getMaxExecutionTimeByMethodName(
            @RequestParam("methodName") @Parameter(description = "Имя метода") String methodName) {
        MethodExecutionDTO maxExecution = methodExecutionTimeRepository
                .getMaxExecutionTimeByMethodName(methodName);
        return ResponseEntity.ok(maxExecution);
    }

}
