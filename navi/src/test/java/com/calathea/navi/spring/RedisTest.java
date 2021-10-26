package com.calathea.navi.spring;

import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootTest
public class RedisTest {
    @Autowired
    RedissonClient redissonClient;

    @Test
    public void testConnection() {
        redissonClient.getKeys().getKeysStream().forEach(it -> System.out.println(it));
    }

    @Test
    public void testAtomicLong() throws ExecutionException, InterruptedException {
        // 멀티 스레드간 공유하는 변수에 대해 다른 스레드에서 변경 됐는지 등을 확인할 수 있는 기능을 포함한 클래스
        AtomicLong localAtomicLong = new AtomicLong(100);

        CompletableFuture future = CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> {
                    RAtomicLong redisAtomicLong = redissonClient.getAtomicLong("Test");

                    System.out.println("1:current value with redis : "+redisAtomicLong);
                    System.out.println("1:current value with local atomic : "+localAtomicLong);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    redisAtomicLong.getAndIncrement(); // redis 에 바로 set
                    localAtomicLong.getAndIncrement(); // 쓰레드 간 공유
                    System.out.println("1:last value with redis : "+redisAtomicLong);
                    System.out.println("1:last value with local atomic : "+localAtomicLong);
                }),
                CompletableFuture.runAsync(() -> {
                    RAtomicLong redisAtomicLong = redissonClient.getAtomicLong("Test");
                    System.out.println("2:current value with redis : "+redisAtomicLong);
                    System.out.println("2:current with local atomic : "+localAtomicLong);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final Long oldValue = localAtomicLong.getAndIncrement(); // 다른 쓰레드에서 값 변경됨
                    System.out.println("2:last value with redis : "+redisAtomicLong);
                    if (localAtomicLong.get() == oldValue) {
                        System.out.println("2:last value with local atomic : "+localAtomicLong);
                    }else {
                        System.out.println("2:old value before changed other thread : "+oldValue);
                        System.out.println("2:last value with local atomic : "+localAtomicLong);
                    }


                })
        );

        future.get();
    }

    @Test
    public void testSimpleStringValue() {
        System.out.println("Key counts : "+redissonClient.getKeys().countExists("Test:String"));
        RBucket<String> simpleString = redissonClient.getBucket("Test:String");
        simpleString.set("Raon's birth day is 12.17");
    }
}
