package com.calathea.navi.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
public class RedissonConfig {
    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.host}")
    private String host;

    /**
     * 커스텀 빈이 아닌 내용은 자동으로 RedissonAutoConfiguration 이 실행됨
     * 지정된 위치에 설정 파일을 넣어놓지 않으면 아래와 같이 config 파일을 정의하고 RedissonClient 빈을 직접 구성해 줘야 함
     * @return
     * @throws IOException
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {
        Config config = new Config();
        String nodeAddress = String.format("redis://%s:%s", host, port);
        config.useSingleServer().setAddress(nodeAddress);
        return Redisson.create(config);
    }
}
