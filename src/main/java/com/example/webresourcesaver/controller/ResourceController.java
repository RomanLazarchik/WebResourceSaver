package com.example.webresourcesaver.controller;

import com.example.webresourcesaver.service.ResourceDownloadService;
import com.example.webresourcesaver.model.UrlRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ResourceController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    private final ResourceDownloadService resourceDownloadService;

    public ResourceController(ResourceDownloadService resourceDownloadService) {
        this.resourceDownloadService = resourceDownloadService;
    }

    @PostMapping("/resource")
    public ResponseEntity<Void> saveResource(@Valid @RequestBody UrlRequest urlRequest) {
        resourceDownloadService.downloadResource(urlRequest.getUrl())
                .exceptionally(error -> {
                    logger.error("Error saving resource: {}", error.getMessage());
                    return null;
                });
        return ResponseEntity.ok().build();
    }
}

