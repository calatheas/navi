package com.calathea.navi.core.service;

import com.calathea.navi.core.repository.AppleRepositoryImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
@AllArgsConstructor
public class AppleService {
    private AppleRepositoryImpl appleRepository;
    private ThreadPoolTaskExecutor executor;

    public CompletableFuture<Long> runAppleAsync(String seed) {
        validate(seed);

        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        log.info("runAppleAsync");
                        Long apple =  appleRepository.growApple();
                        if (seed.isBlank()) {
                            throw new IllegalAccessException("seed is empty");
                        }
                        return apple;
                    } catch (Exception e) {
                        // runAppleAsync 을 호출 한 쪽에서 exceptionally 로직 수행
                        throw new RuntimeException(e);
                    }
                },
                executor
        );
    }

    private void validate(String seed) {
        if (seed == null) {
            throw new RuntimeException("seed is null");
        }
        log.info("validate");
    }

    @Async
    public void runAppleAsyncAnnotation(String seed) {
        validate(seed);
        appleRepository.growApple();
        log.info("runAppleAsyncAnnotation");
    }

    public String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public String getApple() {
        return "Apple";
    }
}
