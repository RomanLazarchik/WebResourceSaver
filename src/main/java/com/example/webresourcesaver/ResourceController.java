package com.example.webresourcesaver;

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
    private final ResourceStorageService resourceStorageService;

    public ResourceController(ResourceDownloadService resourceDownloadService, ResourceStorageService resourceStorageService) {
        this.resourceDownloadService = resourceDownloadService;
        this.resourceStorageService = resourceStorageService;
    }

    @PostMapping("/resource")
    public ResponseEntity<Void> saveResource(@Valid @RequestBody UrlRequest urlRequest) {
        resourceDownloadService.downloadResource(urlRequest.getUrl())
                .thenAccept(data -> resourceStorageService.saveResource(urlRequest.getUrl(), data))
                .exceptionally(error -> {
                    logger.error("Error saving resource: {}", error.getMessage());
                    return null;
                });
        return ResponseEntity.ok().build();
    }
}
