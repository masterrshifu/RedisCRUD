package com.redisexample.springboot.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

public class KeyData implements Serializable {

    @JsonProperty
    @JsonSerialize
    private String field;

    @JsonProperty
    @JsonSerialize
    private String value;

    public KeyData(String key, String value) {
        this.field = key;
        this.value = value;
    }

    // Getters and setters

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
