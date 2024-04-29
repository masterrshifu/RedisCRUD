package com.redisexample.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.List;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class HashData implements Serializable {

    private String hashKey;

    @JsonSerialize
    @JsonProperty
    private List<KeyData> keys;

    // Getters and setters

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public List<KeyData> getKeys() {
        return keys;
    }

    public void setKeys(List<KeyData> keys) {
        this.keys = keys;
    }
}
