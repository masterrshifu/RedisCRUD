package com.redisexample.springboot.service;

import com.redisexample.springboot.entity.HashData;
import com.redisexample.springboot.entity.HashPair;
import com.redisexample.springboot.entity.KeyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class RedisDataService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisDataService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void storeHashPairData(HashPair hashPair) {

        HashOperations<String, byte[], byte[]> hashOperations = redisTemplate.opsForHash();
        byte[] bytes = hashPair.getField().getBytes(StandardCharsets.UTF_8);
        byte[] bytes1 = hashPair.getValue().getBytes(StandardCharsets.UTF_8);
        String hashKey = hashPair.getHashKey();
        hashOperations.put(hashKey, bytes, bytes1);
    }

    public void storeHashData(HashData hashData) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String hashKey = hashData.getHashKey();
        List<KeyData> keys = hashData.getKeys();
        keys.forEach(
                key -> {
                    String field = key.getField();
                    String value = key.getValue();
                    hashOps.put(hashKey, field, value);
                });
    }

    public Map<String, String> retrieveHashData(String hashKey) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        System.out.println(hashOps.entries(hashKey));
        return hashOps.entries(hashKey);
    }

    public void deleteHashData(String hashKey) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(hashKey))) {
            System.out.println("Hash key '" + hashKey + "' does not exist in Redis.");
            return; // Or throw an exception, depending on your use case
        }
        redisTemplate.delete(hashKey);
    }

    public void updateHashData(HashData hashData) {
        String hashKey = hashData.getHashKey();
        List<KeyData> keys = hashData.getKeys();

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        // Retrieve existing hash data
        Map<String, String> existingData = hashOps.entries(hashKey);

        if (Boolean.FALSE.equals(redisTemplate.hasKey(hashKey))) {
            System.out.println("Hash key '" + hashKey + "' does not exist in Redis.");
            return; // Or throw an exception, depending on your use case
        }

        // Update specific fields in the existing data
        // Existing fields in a hashKey can be deleted if the field is passed with a value of null
        for (KeyData key : keys) {
            String field = key.getField();
            String value = key.getValue();
            if (value == null || value.isEmpty()) {
                // If the value is null or empty, delete the field
                if (hashOps.hasKey(hashKey, field)) {
                    hashOps.delete(hashKey, field);
                    System.out.println("Field '" + field + "' deleted from hash '" + hashKey + "'.");
                } else {
                    // Handle the case where the field doesn't exist
                    System.out.println("Field '" + field + "' does not exist in hash '" + hashKey + "'.");
                }
            } else {
                // Otherwise, update or add the field
                hashOps.put(hashKey, field, value);
            }
        }
    }
}