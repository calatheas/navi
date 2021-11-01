package com.calathea.navi.controller;

import com.calathea.navi.constants.CommonCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class}) // junit5 에서 rest docs extension을 사용하기로 함
public class RestDocsTest {
    // Rest template 를 쓰려면 MockMvc 만들때 RestDocumentationExtension 설정이 필요하므로 별도로 생성로직 구현
    private MockMvc mockMvc;

    private RestDocumentationResultHandler documentationHandler;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    public void sample() throws Exception {
        /*
            perform 은 mock 요청을 받아서 ResultActions 을 넘겨줌
            ResultActions 은 예측값으로 테스트 하거나 특정 기능을 수행함
         */
        this.mockMvc.perform(get("/api/health").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("index"));
    }

    @Test
    public void testGetAPI() throws Exception {
        /*
            perform 은 mock 요청을 받아서 ResultActions 을 넘겨줌
            ResultActions 은 예측값으로 테스트 하거나 특정 기능을 수행함
         */
        ResultActions resultActions = mockMvc.perform(
                get("/api/cars")
                        .param("carTypeCode", CommonCodes.CarTypeCode.TRUCK.getCode())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andDo(document("cars"));
    }

    private OperationRequestPreprocessor getDocumentRequest() {
        return Preprocessors.preprocessRequest(
                Preprocessors.modifyUris()
                        .scheme("https")
                        .host("docs.api.com")
                        .removePort(),
                Preprocessors.prettyPrint());
    }

    private OperationResponsePreprocessor getDocumentResponse() {
        return Preprocessors.preprocessResponse(Preprocessors.prettyPrint());
    }

}
