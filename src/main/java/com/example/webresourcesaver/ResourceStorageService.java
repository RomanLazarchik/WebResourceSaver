package com.example.webresourcesaver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class ResourceStorageService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceStorageService.class);

    private final ResourceRepository resourceRepository;

    public ResourceStorageService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public void saveResource(String url, Path path) {
        try {
            Resource resource = new Resource();
            resource.setUrl(url);
            resource.setFilePath(path.toString());

            resourceRepository.save(resource);
            logger.info("Resource saved: {}", url);
        } catch (Exception e) {
            logger.error("Error saving resource: {}", e.getMessage(), e);
        }
    }
}
