package com.calathea.navi.core.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.ses.SesClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class AppleServiceTest {
    @Autowired
    AppleService appleService;

    @Autowired
    SesClient sesClient;

    Logger log = (Logger) LoggerFactory.getLogger(AppleServiceTest.class);

    @Test
    @DisplayName("부분 비동기 실행")
    public void async1() throws ExecutionException, InterruptedException {
        CompletableFuture<Long> completableFuture = appleService.runAppleAsync("seed");
        CompletableFuture<Long> completableFuture2 = appleService.runAppleAsync("");
        CompletableFuture<Long> completableFuture3 = null;
        try {
            completableFuture3 = appleService.runAppleAsync(null);
        }catch (Exception e) {
            log.info("메인 스레드에서 exception 발생");
        }

        CompletableFuture futures = CompletableFuture.allOf(completableFuture, completableFuture2);

        completableFuture
                .thenAccept(s -> {
                    log.info("성공시 이곳으로 들어옴");
                })
                .exceptionally(e -> {
                    log.info("exception 발생시 이곳으로 들어옴");
                    return null;
                });

        completableFuture2
                .thenAccept(s -> {
                    log.info("성공시 이곳으로 들어옴");
                })
                .exceptionally(e -> {
                    log.info("exception 발생시 이곳으로 들어옴");
                    return null;
                });

        log.info("메인 스레드 대기하고 비동기 스레드 시작됨, 메인 스레드 바로 종료해도 비동기 스레드는 계속 실행됨");

        futures.get();
        Assertions.assertTrue(futures.isCompletedExceptionally());
    }


    @Test
    @DisplayName("@Async annotation 실행")
    public void async2() throws InterruptedException {
        appleService.runAppleAsyncAnnotation("seed");

        log.info("메인 스레드 종료하고 비동기 스레드 시작됨");

        // 자식 스레드 종료되지 않도록 기다림
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void getApple() {
        appleService.getApple();
    }
}