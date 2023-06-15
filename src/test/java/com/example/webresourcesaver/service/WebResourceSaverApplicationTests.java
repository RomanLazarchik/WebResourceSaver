package com.example.webresourcesaver.service;

import com.example.webresourcesaver.model.UrlRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WebResourceSaverApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResourceDownloadService resourceDownloadService;

    @Test
    public void saveResource_success() throws Exception {
        UrlRequest urlRequest = new UrlRequest();
        urlRequest.setUrl("http://example.com");
        when(resourceDownloadService.downloadResource(any(String.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        mockMvc.perform(post("/resource")
                        .content("{\"url\":\"http://example.com\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(resourceDownloadService, times(1)).downloadResource(any(String.class));
    }

    @Test
    public void saveResource_fail_invalidUrl() throws Exception {
        mockMvc.perform(post("/resource")
                        .content("{\"url\":\"invalidurl\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }
}

