package com.zzyl.nursing.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HelloJob {

//    @Scheduled(cron = "0 0 1 * * ?")  //代表：每5秒钟执行一次
    public void execute() {
        System.out.println("Hello Job!"+ LocalDateTime.now());
    }

    public void myJob(){
        System.out.println("hello myJob" + LocalDateTime.now());
    }
}
