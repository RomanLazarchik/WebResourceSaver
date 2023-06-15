package com.example.webresourcesaver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import org.springframework.core.io.buffer.DataBufferUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ResourceStorageService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceStorageService.class);
    private String fileExtension;

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
    private String fileName;

    public String getFileName() {
        return fileName;
    }


    public Mono<Void> saveBinaryPart(Flux<DataBuffer> dataBufferFlux) {
        fileName = UUID.randomUUID() + fileExtension;
        logger.info("File name: {}", fileName);
        Path filePath = Path.of("C:\\downloads\\" + fileName);

        return DataBufferUtils.write(dataBufferFlux, filePath, StandardOpenOption.WRITE, StandardOpenOption.CREATE)
                .then();
    }
}






