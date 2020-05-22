package com.example.lab5;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Url {
    @JsonProperty("url")
    private String url;

    public Url() {
    }

    public String getUrl() {
        return url;
    }
}
