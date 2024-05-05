package com.volga.MethodTrackTime.dao;

import com.volga.MethodTrackTime.domain.MethodExecutionTime;
import com.volga.MethodTrackTime.model.dto.MethodExecutionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MethodExecutionTimeRepository extends JpaRepository<MethodExecutionTime, Long> {
    MethodExecutionTime save(MethodExecutionTime methodExecutionTime);

    @Query("SELECT m.executionTime, m.methodName " +
            "FROM MethodExecutionTime m " +
            "WHERE m.className = :className")
    List<Object[]> getListExecutionTimeByClass(@Param("className") String className);

    @Query("SELECT AVG(m.executionTime) " +
            "FROM MethodExecutionTime m " +
            "WHERE m.className = :className")
    Double getAvgExecutionTimeByClass(@Param("className") String className);

    @Query("SELECT MAX(m.executionTime) AS maxExecutionTime, m.methodName " +
            "FROM MethodExecutionTime m " +
            "WHERE m.className = :className " +
            "GROUP BY m.methodName")
    List<Object[]> getMaxExecutionTimeByClass(@Param("className") String className);

    @Query("SELECT new com.volga.MethodTrackTime.model.dto.MethodExecutionDTO(m.executionTime, m.methodName) " +
            "FROM MethodExecutionTime m " +
            "WHERE m.methodName = :methodName")
    List<MethodExecutionDTO> getExecutionTimeByMethodName(@Param("methodName") String methodName);

    @Query("SELECT new com.volga.MethodTrackTime.model.dto.MethodExecutionDTO(AVG(m.executionTime), m.methodName)" +
            "FROM MethodExecutionTime m " +
            "WHERE m.methodName = :methodName " +
            "GROUP BY m.methodName")
    MethodExecutionDTO getAvgExecutionTimeByMethodName(@Param("methodName") String methodName);

    @Query("SELECT new com.volga.MethodTrackTime.model.dto.MethodExecutionDTO(MAX(m.executionTime), m.methodName) " +
            "FROM MethodExecutionTime m " +
            "WHERE m.methodName = :methodName " +
            "GROUP BY m.methodName")
    MethodExecutionDTO getMaxExecutionTimeByMethodName(@Param("methodName") String methodName);
}