package com.redisexample.springboot.controller;

import com.redisexample.springboot.dataModels.HashingData;
import com.redisexample.springboot.entity.HashData;
import com.redisexample.springboot.service.RedisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

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
    public ResponseEntity<String> saveData(@RequestBody HashingData hashData) {
        redisDataService.saveHashData(hashData);
        return new ResponseEntity<>("Data stored successfully!", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateData(@RequestBody HashingData hashData) {
        redisDataService.updateHashData(hashData);
        return new ResponseEntity<>("Data updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/delete/hashKeys")
    public ResponseEntity<String> deleteData(@RequestBody HashingData hashingData) {
        redisDataService.deleteHashData(hashingData);
        return ResponseEntity.ok("The hash keys have been deleted");
    }

    @DeleteMapping("/delete/fieldsInHashKeys")
    public ResponseEntity<String> deleteSpecificData(@RequestBody HashingData hashingData) {
        redisDataService.deleteFieldInHash(hashingData);
        return ResponseEntity.ok("The fields have been deleted");
    }

    @GetMapping("/retrieve")
    public ResponseEntity<Map<String, String>> retrieveData(@RequestParam String hashKey) {
        return new ResponseEntity<>(redisDataService.retrieveHashData(hashKey), HttpStatus.OK);
    }

    @GetMapping("/retrieveAllHashes")
    public ResponseEntity<Set<String>> retrieveData() {
        return new ResponseEntity<>(redisDataService.getAllHashes(), HttpStatus.OK);
    }

    @GetMapping("/retrieveAllHashesAndKeys")
    public ResponseEntity<Map<String, Map<String, String>>> getAllHashesWithKeys() {
        return new ResponseEntity<>(redisDataService.getAllHashesWithKeysAndValues(), HttpStatus.OK);
    }

    // this method allows you to update values of multiple fields in multiple hashes
    @PutMapping("/hashes/updateValue")
    public ResponseEntity<String> updateFieldValueInHash(@RequestBody HashingData hashingData) {
        redisDataService.updateFieldsValueInHash(hashingData);
        return new ResponseEntity<>("The field value has been updated", HttpStatus.OK);
    }

    @PutMapping("/hashes/updateFields")
    public ResponseEntity<String> updateFieldInHash(@RequestBody HashingData hashingData) {
        redisDataService.updateFieldsInHash(hashingData);
        return ResponseEntity.ok("Fields have been updated");
    }

}
