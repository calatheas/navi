package com.calathea.navi.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
//@Configuration RedissonAutoConfiguration도 일단 제외
public class RedissonConfig {
    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.host}")
    private Resource host;

    /**
     * 커스텀 빈이 아닌 내용은 자동으로 RedissonAutoConfiguration 이 실행됨
     * 지정된 위치에 설정 파일을 넣어놓지 않으면 아래와 같이 config 파일을 정의하고 RedissonClient 빈을 직접 구성해 줘야 함
     * @return
     * @throws IOException
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {
        Config config = new Config();
        String host = new String(this.host.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
        String nodeAddress = String.format("redis://%s:%s", host, port);
        config.useClusterServers()
                .setNodeAddresses(List.of(nodeAddress));
        return Redisson.create(config);
    }
}
