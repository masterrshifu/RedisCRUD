package com.redisexample.springboot.service;

import com.redisexample.springboot.dataModels.HashingData;
import com.redisexample.springboot.entity.HashData;
import com.redisexample.springboot.entity.KeyData;
import com.redisexample.springboot.exceptions.ErrorCode;
import com.redisexample.springboot.exceptions.NoSuchFieldFoundException;
import com.redisexample.springboot.exceptions.NoSuchKeyFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RedisDataService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisDataService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
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

    public void saveHashData(HashingData hashingData) {

        List<HashData> hashDataList = hashingData.getHashData();

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        for(HashData hashData: hashDataList) {
            String hashKey = hashData.getHashKey();
            List<KeyData> keys = hashData.getKeys();
            keys.forEach(
                    key -> {
                        String field = key.getField();
                        String value = key.getValue();
                        hashOps.put(hashKey, field, value);
                    });
        }

    }

    public Map<String, String> retrieveHashData(String hashKey) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        System.out.println(hashOps.entries(hashKey));
        return hashOps.entries(hashKey);
    }

    public void deleteHashData(HashingData hashingData) {

        List<HashData> hashDataList = hashingData.getHashData();

        for(HashData hashData: hashDataList) {
            String hashKey = hashData.getHashKey();
            if (Boolean.FALSE.equals(redisTemplate.hasKey(hashKey))) {
                System.out.println("Hash key '" + hashKey + "' does not exist in Redis.");
                throw new NoSuchKeyFoundException(ErrorCode.ERROR_KEY_NOT_FOUND); // Or throw an exception, depending on your use case
            }
            redisTemplate.delete(hashKey);
        }

    }

    public void deleteFieldInHash(HashingData hashingData) {

        List<HashData> hashDataList = hashingData.getHashData();

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        for(HashData hashData: hashDataList) {
            String hashKey = hashData.getHashKey();

            if (Boolean.FALSE.equals(redisTemplate.hasKey(hashKey))) {
                System.out.println("Hash key '" + hashKey + "' does not exist in Redis.");
                throw new NoSuchKeyFoundException(ErrorCode.ERROR_KEY_NOT_FOUND); // Or throw an exception, depending on your use case
            }

            List<KeyData> keys = hashData.getKeys();

            for(KeyData key: keys) {
                String field = key.getField();
                if (hashOps.hasKey(hashKey, field)) {
                    hashOps.delete(hashKey, field);
                } else {
                    // Handle the case where the field doesn't exist
                    throw new NoSuchFieldFoundException(ErrorCode.ERROR_FIELD_NOT_FOUND); // Or throw an exception, depending on your use case
                }
            }

        }




    }

    public void updateHashData(HashingData hashingData) {

        List<HashData> hashDataList = hashingData.getHashData();

        for(HashData hashData : hashDataList) {
            String hashKey = hashData.getHashKey();
            List<KeyData> keys = hashData.getKeys();

            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

            if (Boolean.FALSE.equals(redisTemplate.hasKey(hashKey))) {
                System.out.println("Hash key '" + hashKey + "' does not exist in Redis.");
                throw new NoSuchKeyFoundException(ErrorCode.ERROR_KEY_NOT_FOUND); // Or throw an exception, depending on your use case
            }

            // Update specific fields in the existing data
            for (KeyData key : keys) {
                String field = key.getField();
                String value = key.getValue();
                hashOps.put(hashKey, field, value);
            }
        }

    }

    public Set<String> getAllHashes() {
        return redisTemplate.keys("*");
    }

    public Map<String, Map<String, String>> getAllHashesWithKeysAndValues() {
        Set<String> hashKeys = redisTemplate.keys("*");

        Map<String, Map<String, String>> allHashes = new HashMap<>();

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        for (String hashKey : hashKeys) {
            allHashes.put(hashKey, hashOps.entries(hashKey));
        }

        return allHashes;
    }

    public void updateFieldValueInHash(String hashKey, String field, String newValue) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(hashKey))) {
            throw new NoSuchKeyFoundException(ErrorCode.ERROR_KEY_NOT_FOUND); // Or throw an exception, depending on your use case
        }

        if (hashOps.hasKey(hashKey, field)) {
            hashOps.put(hashKey, field, newValue);
        } else {
            // Handle the case where the field doesn't exist
            throw new NoSuchFieldFoundException(ErrorCode.ERROR_FIELD_NOT_FOUND); // Or throw an exception, depending on your use case
        }
    }

    public void updateFieldsValueInHash(HashingData hashingData) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        List<HashData> hashDataList = hashingData.getHashData();

        for(HashData hashData: hashDataList) {
            String hashKey = hashData.getHashKey();

            if (Boolean.FALSE.equals(redisTemplate.hasKey(hashKey))) {
                throw new NoSuchKeyFoundException(ErrorCode.ERROR_KEY_NOT_FOUND); // Or throw an exception, depending on your use case
            }

            List<KeyData> keys = hashData.getKeys();

            for(KeyData key: keys) {
                String field = key.getField();
                String newValue = key.getValue();

                if (hashOps.hasKey(hashKey, field)) {
                    hashOps.put(hashKey, field, newValue);
                } else {
                    // Handle the case where the field doesn't exist
                    throw new NoSuchFieldFoundException(ErrorCode.ERROR_FIELD_NOT_FOUND); // Or throw an exception, depending on your use case
                }

            }

        }

    }

    public void updateFieldInHash(String hashKey, String oldField, String newField, String value) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(hashKey))) {
            throw new NoSuchKeyFoundException(ErrorCode.ERROR_KEY_NOT_FOUND); // Or throw an exception, depending on your use case
        }

        if (hashOps.hasKey(hashKey, oldField)) {
            String oldValue = hashOps.get(hashKey, oldField);
            hashOps.delete(hashKey, oldField); // Delete old field
            hashOps.put(hashKey, newField, value != null ? value : oldValue); // Add new field
        } else {
            // Handle the case where the field doesn't exist
            throw new NoSuchKeyFoundException(ErrorCode.ERROR_KEY_NOT_FOUND); // Or throw an exception, depending on your use case
        }
    }

    public void updateFieldsInHash(HashingData hashingData) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        List<HashData> hashDataList = hashingData.getHashData();

        for(HashData hashData: hashDataList) {
            String hashKey = hashData.getHashKey();
            List<KeyData> keys = hashData.getKeys();

            for(KeyData key: keys) {
                String oldField = key.getField();
                String newField = key.getNewField();
                String value = key.getValue();

                if (Boolean.FALSE.equals(redisTemplate.hasKey(hashKey))) {
                    throw new NoSuchKeyFoundException(ErrorCode.ERROR_KEY_NOT_FOUND); // Or throw an exception, depending on your use case
                }

                if (hashOps.hasKey(hashKey, oldField)) {
                    String oldValue = hashOps.get(hashKey, oldField);
                    hashOps.delete(hashKey, oldField); // Delete old field
                    hashOps.put(hashKey, newField, value != null ? value : oldValue); // Add new field
                } else {
                    // Handle the case where the field doesn't exist
                    throw new NoSuchKeyFoundException(ErrorCode.ERROR_KEY_NOT_FOUND); // Or throw an exception, depending on your use case
                }

            }
        }


    }

}
