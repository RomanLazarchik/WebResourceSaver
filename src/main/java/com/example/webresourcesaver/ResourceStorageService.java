package com.example.webresourcesaver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ResourceStorageService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceStorageService.class);

    private final ResourceRepository resourceRepository;

    public ResourceStorageService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public void saveResource(String url, byte[] data) {
        Resource resource = new Resource();
        resource.setUrl(url);
        resource.setData(data);

        resourceRepository.save(resource);
        logger.info("Resource saved: {}", url);
    }
}
