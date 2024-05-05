package com.volga.MethodTrackTime.service;

import com.volga.MethodTrackTime.annotation.TrackAsyncTime;
import com.volga.MethodTrackTime.annotation.TrackTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class InfoExampleService {

    @TrackTime
    public void info(){
        System.out.println("Method info");
    }

    @TrackAsyncTime
    @Async
    public CompletableFuture asyncInfo() throws ExecutionException, InterruptedException {
        CompletableFuture<String> text = new CompletableFuture<>();
        text.complete("Async method info");
        System.out.println(text.get());
        return text;
    }
}
