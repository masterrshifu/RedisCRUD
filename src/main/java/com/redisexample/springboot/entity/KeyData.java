package com.redisexample.springboot.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyData implements Serializable {

    @JsonProperty
    @JsonSerialize
    private String field;

    private String newField;

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

    public String getNewField() {
        return newField;
    }

    public void setNewField(String newField) {
        this.newField = newField;
    }
}
