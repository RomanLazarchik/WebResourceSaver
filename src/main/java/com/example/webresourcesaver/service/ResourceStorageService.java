package com.example.webresourcesaver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ResourceStorageService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceStorageService.class);
    private String fileExtension;
    private final Path basePath;

    public ResourceStorageService(@Value("${file.storage.path}") String basePath) {
        this.basePath = Path.of(basePath);
        if (!Files.exists(this.basePath)) {
            throw new RuntimeException("Path specified in 'file.storage.path' does not exist: " + basePath);
        }
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Path filePath;

    public String getFilePath() {
        return filePath.toString();
    }

    public Mono<Void> saveBinaryPart(Flux<DataBuffer> dataBufferFlux) {
        String fileName = UUID.randomUUID() + fileExtension;
        logger.info("File name: {}", fileName);
        filePath = basePath.resolve(fileName);

        return DataBufferUtils.write(dataBufferFlux, filePath, StandardOpenOption.WRITE, StandardOpenOption.CREATE)
                .then();
    }
}
