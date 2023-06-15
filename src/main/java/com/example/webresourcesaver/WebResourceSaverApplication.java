package com.example.webresourcesaver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WebResourceSaverApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebResourceSaverApplication.class, args);
    }

}
