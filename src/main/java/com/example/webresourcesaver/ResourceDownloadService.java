package com.example.webresourcesaver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

@Service
public class ResourceDownloadService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceDownloadService.class);
    private final WebClient webClient;
    private final ResourceStorageService resourceStorageService;
    private final ResourceRepository resourceRepository;

    public ResourceDownloadService(WebClient.Builder webClientBuilder,
                                   ResourceStorageService resourceStorageService,
                                   ResourceRepository resourceRepository) { // Inject repository
        this.webClient = webClientBuilder.build();
        this.resourceStorageService = resourceStorageService;
        this.resourceRepository = resourceRepository; // Initialize repository
    }


    @Async("asyncExecutor")
    public CompletableFuture<Void> downloadResource(String url) {
        logger.info("Attempting to download resource: {}", url);
        return webClient.get().uri(url)
                .exchangeToMono(clientResponse -> {
                    String contentType = clientResponse.headers().contentType()
                            .map(org.springframework.util.MimeType::toString)
                            .orElse("");
                    logger.info("Content type: {}", contentType);
                    String extension = getExtensionFromMimeType(contentType);
                    resourceStorageService.setFileExtension(extension);
                    return clientResponse.bodyToFlux(DataBuffer.class)
                            .transform(resourceStorageService::saveBinaryPart)
                            .doOnError(e -> logger.error("Error downloading resource from url: {}", url, e))
                            .then()
                            .doOnSuccess(aVoid -> {
                                Resource resource = new Resource();
                                resource.setUrl(url);
                                resource.setFilePath(resourceStorageService.getFileName()); // используем метод getFileName
                                resourceRepository.save(resource);
                            });
                })
                .toFuture();
    }

    private String getExtensionFromMimeType(String mimeType) {
        if (mimeType == null || mimeType.isEmpty()) {
            return "";
        }

        String baseMimeType = mimeType.split(";")[0]; // разделить MIME-тип и параметры

        if ("text/html".equals(baseMimeType)) {
            return ".html";
        }

        if ("audio/mpeg".equals(baseMimeType)) {
            return ".mp3";
        }

        // add other special cases here

        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
        try {
            MimeType mt = allTypes.forName(baseMimeType); // использовать baseMimeType здесь
            return mt.getExtension();
        } catch (MimeTypeException e) {
            return "";  // return empty string if MIME type is not known
        }
    }
}





