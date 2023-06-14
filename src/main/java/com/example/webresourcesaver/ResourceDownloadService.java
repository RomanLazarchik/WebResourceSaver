package com.example.webresourcesaver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.concurrent.CompletableFuture;

@Service
public class ResourceDownloadService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceDownloadService.class);

    private final WebClient webClient;

    public ResourceDownloadService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Async("asyncExecutor")
    public CompletableFuture<byte[]> downloadResource(String url) {
        logger.info("Attempting to download resource: {}", url);
        return webClient.get().uri(url)
                .retrieve()
                .bodyToMono(byte[].class)
                .toFuture()
                .exceptionally(throwable -> {
                    logger.error("Error downloading resource from url: {}", url, throwable);
                    throw new ResourceDownloadException("Error downloading resource from url: " + url, throwable);
                });
    }
}

