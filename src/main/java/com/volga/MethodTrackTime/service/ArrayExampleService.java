package com.volga.MethodTrackTime.service;

import com.volga.MethodTrackTime.annotation.TrackAsyncTime;
import com.volga.MethodTrackTime.annotation.TrackTime;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class ArrayExampleService {

    @TrackTime
    public void add() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add((int) (Math.random() * 100));
        }
    }

    @TrackAsyncTime
    @Async
    public CompletableFuture asyncAdd() {
        CompletableFuture<String> text = new CompletableFuture<>();
        text.complete("go");
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add((int) (Math.random() * 100));
        }
        return text;
    }


}
