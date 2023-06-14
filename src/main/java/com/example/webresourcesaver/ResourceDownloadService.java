package com.example.webresourcesaver;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
public class ResourceDownloadService {

    private final WebClient webClient;

    public ResourceDownloadService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Async("asyncExecutor")
    public CompletableFuture<byte[]> downloadResource(String url) {
        return webClient.get().uri(url)
                .retrieve()
                .bodyToMono(byte[].class)
                .toFuture();
    }
}

