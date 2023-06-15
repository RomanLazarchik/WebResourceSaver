package com.example.webresourcesaver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;

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
    private final Path basePath; // путь будет передан через конфигурацию

    public ResourceStorageService(@Value("${file.storage.path}") String basePath) {
        this.basePath = Path.of(basePath);
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Path filePath;

    public String getFilePath() {
        return filePath.toString(); // вернуть полный путь к файлу в виде строки
    }

    public Mono<Void> saveBinaryPart(Flux<DataBuffer> dataBufferFlux) {
        String fileName = UUID.randomUUID() + fileExtension;
        logger.info("File name: {}", fileName);
        filePath = basePath.resolve(fileName); // сохранить полный путь к файлу

        return DataBufferUtils.write(dataBufferFlux, filePath, StandardOpenOption.WRITE, StandardOpenOption.CREATE)
                .then();
    }
}







