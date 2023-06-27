package com.example.webresourcesaver.model;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UrlRequest {

    @Pattern(regexp = "^(https?://(www\\.)?)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)$",
            message = "Invalid URL")
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }
}
