package com.example.webresourcesaver.exception;

import com.example.webresourcesaver.service.ResourceDownloadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResourceDownloadService resourceDownloadService;

    @BeforeEach
    public void setUp() {
        Mockito.when(resourceDownloadService.downloadResource(Mockito.anyString()))
                .thenThrow(new ResourceDownloadException("Test exception", new Exception()));
    }

    @Test
    public void whenResourceDownloadException_thenReturns500() throws Exception {
        String urlRequestJson = "{ \"url\" : \"http://test.com\"}";

        ResultActions resultActions = mockMvc.perform(
                post("/resource")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(urlRequestJson)
        );

        resultActions.andExpect(status().isInternalServerError())
                .andExpect(content().string("Test exception"));
    }
}
