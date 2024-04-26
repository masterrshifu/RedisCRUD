package com.redisexample.springboot.controller;

import com.redisexample.springboot.entity.HashData;
import com.redisexample.springboot.entity.HashPair;
import com.redisexample.springboot.service.RedisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RedisDataController {

    private final RedisDataService redisDataService;
    @Autowired
    public RedisDataController(RedisDataService redisDataService) {
        this.redisDataService = redisDataService;
    }

    @PostMapping("/store")
    public ResponseEntity<String> storeData(@RequestBody HashData hashData) {
        redisDataService.storeHashData(hashData);
        return new ResponseEntity<>("Data stored successfully!", HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> storeHashPairData(@RequestBody HashPair hashData) {
        redisDataService.storeHashPairData(hashData);
        return new ResponseEntity<>("Data stored successfully!", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateData(@RequestBody HashData hashData) {
        redisDataService.updateHashData(hashData);
        return new ResponseEntity<>("Data updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{hashKey}")
    public ResponseEntity<String> deleteData(@PathVariable String hashKey) {
        redisDataService.deleteHashData(hashKey);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/retrieve/{hashKey}")
    public ResponseEntity<Map<String, String>> retrieveData(@PathVariable String hashKey) {
        return new ResponseEntity<>(redisDataService.retrieveHashData(hashKey), HttpStatus.OK);
    }

}
