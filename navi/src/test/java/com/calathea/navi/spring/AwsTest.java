package com.calathea.navi.spring;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendEmailResponse;

import java.util.List;

import static com.calathea.navi.util.StringEncoder.utf8To8859;

@SpringBootTest
public class AwsTest {
    @Autowired
    SesClient sesClient;

    @Value("${aws.ses.sender}")
    private String sender;

    @Test
    @Disabled
    public void awsTest() {
        String sender = "어드민<calatheas@naver.com>";

        Destination destination = Destination.builder().toAddresses(List.of("calathea@naver.com")).build();
        String subject = "SES 테스트 입니다.";
        String htmlBody = "<p><span style=\"font-size: 24pt;\">아이디를 인증하려면 아래 링크로 이동해주세요.</span></p><p><a href=\"https://translate.google.co.kr\" target=\"_self\" style=\"cursor: pointer; white-space: pre;\">https://translate.google.co.kr</a><span id=\"husky_bookmark_end_1633596303060\"></span><br></p>";
        Message message = Message.builder()
                .subject(Content.builder().data(subject).build()) // 제목
                .body(Body.builder().html(Content.builder().data(htmlBody).build()).build()) // 본문
                .build();
        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .source(utf8To8859(sender))
                .destination(destination)
                .message(message)
                .build();
        SendEmailResponse response = sesClient.sendEmail(sendEmailRequest);
        System.out.println(response);
    }
}
