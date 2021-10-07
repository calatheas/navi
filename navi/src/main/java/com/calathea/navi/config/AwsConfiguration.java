package com.calathea.navi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Slf4j
@Configuration
public class AwsConfiguration {

    @Value("${aws.region}")
    private String region;

    public AwsCredentialsProvider awsCredentialsProvider() {
        // /Users/calathea/.aws/credentials 이용
        return ProfileCredentialsProvider
                .builder()
                .build();
//        // java 시스템 변수 > 환경변수 > 파일 순서로 찾기
//        return DefaultCredentialsProvider
//                .builder()
//                .build();
    }

    @Bean
    public SesClient sesClient() {
        return SesClient.builder()
                .credentialsProvider(awsCredentialsProvider())
                .region(Region.of(region))
                .build();
    }
}
