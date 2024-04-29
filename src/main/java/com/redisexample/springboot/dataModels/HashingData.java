package com.redisexample.springboot.dataModels;

import com.redisexample.springboot.entity.HashData;

import java.util.List;

public class HashingData {

    private List<HashData> hashData;

    public List<HashData> getHashData() {
        return hashData;
    }

    public void setHashData(List<HashData> hashData) {
        this.hashData = hashData;
    }
}
