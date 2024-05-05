package com.volga.MethodTrackTime.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность времени выполнения метода")
public class MethodExecutionDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Время выполнения в мс")
    private Long executionTime;
    @Schema(description = "Название метода")
    private String methodName;
    @Schema(description = "Среднее время выполнения")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double avgExecutionTime;

    public MethodExecutionDTO(Long executionTime, String methodName) {
        this.executionTime = executionTime;
        this.methodName = methodName;
    }

    public MethodExecutionDTO(Double avgExecutionTime, String methodName) {
        this.avgExecutionTime = avgExecutionTime;
        this.methodName = methodName;
    }
}
