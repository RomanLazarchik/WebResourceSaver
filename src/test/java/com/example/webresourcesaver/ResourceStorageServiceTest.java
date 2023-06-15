package com.example.webresourcesaver;

import com.example.webresourcesaver.service.ResourceStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResourceStorageServiceTest {

    private static final String FILE_EXTENSION = ".txt";
    private static final String TEST_DATA = "Hello, World!";
    private ResourceStorageService resourceStorageService;
    private DefaultDataBufferFactory dataBufferFactory;
    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("testDir");
        resourceStorageService = new ResourceStorageService(tempDir.toString());
        dataBufferFactory = new DefaultDataBufferFactory();
        resourceStorageService.setFileExtension(FILE_EXTENSION);
    }

    @Test
    void saveBinaryPart_shouldSaveToFileAndReturnVoid_whenDataBufferFluxProvided() throws Exception {
        DefaultDataBuffer buffer = dataBufferFactory.wrap(TEST_DATA.getBytes());
        Flux<DataBuffer> dataBufferFlux = Flux.just(buffer);

        resourceStorageService.saveBinaryPart(dataBufferFlux).block();

        String savedFilePath = resourceStorageService.getFilePath();
        Path savedFile = Paths.get(savedFilePath);

        assertTrue(Files.exists(savedFile));
        assertEquals(TEST_DATA, Files.readString(savedFile));
    }

    @Test
    void getFilePath_shouldReturnFilePathAsString() {
        String fileName = tempDir.resolve(UUID.randomUUID().toString() + FILE_EXTENSION).toString();
        resourceStorageService.filePath = Path.of(fileName);

        String resultFilePath = resourceStorageService.getFilePath();
        assertTrue(resultFilePath.endsWith(FILE_EXTENSION));
        assertEquals(resultFilePath, fileName);
    }

    @Test
    void saveBinaryPart_shouldHandleFluxError() {
        Flux<DataBuffer> errorFlux = Flux.error(new RuntimeException("Error Flux"));

        StepVerifier.create(resourceStorageService.saveBinaryPart(errorFlux))
                .verifyErrorMatches(e -> e.getMessage().contains("Error Flux"));
    }
}



