package com.example.webresourcesaver;

public class ResourceDownloadException extends RuntimeException {
    public ResourceDownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}
