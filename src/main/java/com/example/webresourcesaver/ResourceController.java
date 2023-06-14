package com.example.webresourcesaver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    private final ResourceDownloadService resourceDownloadService;
    private final ResourceStorageService resourceStorageService;

    public ResourceController(ResourceDownloadService resourceDownloadService, ResourceStorageService resourceStorageService) {
        this.resourceDownloadService = resourceDownloadService;
        this.resourceStorageService = resourceStorageService;
    }

    @PostMapping("/resource")
    public ResponseEntity<Void> saveResource(@RequestBody String url) {
        resourceDownloadService.downloadResource(url)
                .thenAccept(data -> resourceStorageService.saveResource(url, data))
                .exceptionally(error -> {
                    System.err.println("Error saving resource: " + error.getMessage());
                    error.printStackTrace();
                    return null;
                });
        return ResponseEntity.ok().build();
    }
}
