package com.example.webresourcesaver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@Service
public class ResourceDownloadService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceDownloadService.class);

    private final WebClient webClient;

    public ResourceDownloadService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Async("asyncExecutor")
    public CompletableFuture<Path> downloadResource(String url) {
        logger.info("Attempting to download resource: {}", url);
        Path filePath = Paths.get("C:\\downloads\\" + url.hashCode());
        File file = filePath.toFile();

        Flux<DataBuffer> dataBufferFlux = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(DataBuffer.class);

        dataBufferFlux.subscribe(dataBuffer -> {
            try (FileOutputStream out = new FileOutputStream(file, true);
                 InputStream is = dataBuffer.asInputStream()) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                logger.error("Error writing data to file: {}", filePath, e);
                throw new ResourceDownloadException("Error writing data to file: " + filePath, e);
            }
        });

        return CompletableFuture.completedFuture(filePath)
                .exceptionally(throwable -> {
                    logger.error("Error downloading resource from url: {}", url, throwable);
                    throw new ResourceDownloadException("Error downloading resource from url: " + url, throwable);
                });
    }
}


