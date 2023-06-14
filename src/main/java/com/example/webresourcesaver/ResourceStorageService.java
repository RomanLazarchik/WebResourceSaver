package com.example.webresourcesaver;

import org.springframework.stereotype.Service;

@Service
public class ResourceStorageService {

    private final ResourceRepository resourceRepository;

    public ResourceStorageService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public void saveResource(String url, byte[] data) {
        Resource resource = new Resource();
        resource.setUrl(url);
        resource.setData(data);

        resourceRepository.save(resource);
    }
}
