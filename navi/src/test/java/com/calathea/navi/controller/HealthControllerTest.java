package com.calathea.navi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
Testing the Web Layer with MockMvc

1. The full Spring application context is started but without the server (219ms)
@SpringBootTest
@AutoConfigureMockMvc

2. We can narrow the tests to only the web layer by using @WebMvcTest + controller class
@WebMvcTest(HealthController.class) // 테스트 하는 컨트롤러 + 컨트롤러에서 사용하는 서비스는 mocking 필요

아래와 같이...
@MockBean
private GreetingService service;
when(service.greet()).thenReturn("Hello, Mock");

테스트 결과 샘플애플리케이션 정도면 1초 밖에 차이 안나는 듯...

 */
@SpringBootTest
@AutoConfigureMockMvc
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /*
    또는
    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
     */

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/api/health")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Active")));
    }
}