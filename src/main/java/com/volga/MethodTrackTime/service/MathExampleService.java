package com.volga.MethodTrackTime.service;

import com.volga.MethodTrackTime.annotation.TrackAsyncTime;
import com.volga.MethodTrackTime.annotation.TrackTime;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MathExampleService {

    @TrackTime
    public int sum(int a, int b) {
        int result = 0;
        for (int i = 0; i < 10000000; i++) {
            result += a + b;
        }
        return result;
    }

    @Async
    @TrackAsyncTime
    public CompletableFuture asyncSum(int a, int b) {
        CompletableFuture<Integer> x = new CompletableFuture<>();
        x.complete(8);
        int result = 0;
        for (int i = 0; i < 10000000; i++) {
            result += a + b;
        }
        return x;
    }

}

