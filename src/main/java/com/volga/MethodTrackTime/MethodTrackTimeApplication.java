package com.volga.MethodTrackTime;

import com.volga.MethodTrackTime.service.ArrayExampleService;
import com.volga.MethodTrackTime.service.InfoExampleService;
import com.volga.MethodTrackTime.service.MathExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan("com.volga.MethodTrackTime")
public class MethodTrackTimeApplication {

    private final InfoExampleService infoExampleService;
    private final MathExampleService mathExampleService;

    private final ArrayExampleService arrayExampleService;

    public static void main(String[] args) {
        SpringApplication.run(MethodTrackTimeApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 11; i++) {
            infoExampleService.asyncInfo();
            infoExampleService.info();
            mathExampleService.asyncSum(3, 3);
            mathExampleService.sum(1, 1);
            arrayExampleService.asyncAdd();
            arrayExampleService.add();
        }
    }
}
