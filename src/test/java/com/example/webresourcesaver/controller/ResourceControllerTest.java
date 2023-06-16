package com.example.webresourcesaver.controller;

import com.example.webresourcesaver.model.UrlRequest;
import com.example.webresourcesaver.service.ResourceDownloadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ResourceControllerTest {

    @Mock
    private ResourceDownloadService resourceDownloadService;

    private ResourceController resourceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        resourceController = new ResourceController(resourceDownloadService);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void testSaveResource() {
        String url = "http://testurl.com";
        UrlRequest urlRequest = new UrlRequest();
        urlRequest.setUrl(url);

        when(resourceDownloadService.downloadResource(anyString())).thenReturn(CompletableFuture.completedFuture(null));

        ResponseEntity<Void> responseEntity = resourceController.saveResource(urlRequest);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(resourceDownloadService, times(1)).downloadResource(url);
    }
}
