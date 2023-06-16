package com.example.webresourcesaver.config;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.*;

public class AsyncConfigurationTest {

    private AsyncConfiguration asyncConfiguration = new AsyncConfiguration();

    @Test
    public void testAsyncExecutor() {
        Executor executor = asyncConfiguration.asyncExecutor();

        assertNotNull(executor, "Executor is null");
        assertTrue(executor instanceof ThreadPoolTaskExecutor, "Executor is not an instance of ThreadPoolTaskExecutor");

        ThreadPoolTaskExecutor threadPoolExecutor = (ThreadPoolTaskExecutor) executor;
        assertEquals(2, threadPoolExecutor.getCorePoolSize(), "Core pool size does not match");
        assertEquals(2, threadPoolExecutor.getMaxPoolSize(), "Max pool size does not match");
        assertEquals(500, threadPoolExecutor.getQueueCapacity(), "Queue capacity does not match");
        assertEquals("Async-", threadPoolExecutor.getThreadNamePrefix(), "Thread name prefix does not match");
    }
}



