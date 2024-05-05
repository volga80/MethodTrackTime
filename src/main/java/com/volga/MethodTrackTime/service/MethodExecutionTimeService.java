package com.volga.MethodTrackTime.service;

public interface MethodExecutionTimeService {
    void save(String className, String methodName, long executionTime);
}
