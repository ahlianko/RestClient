package com.andriihlianko.testproject.controller;

import com.andriihlianko.testproject.database.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/data")
public class SyncController {
    @Autowired
    private CustomerRepository repository;
    @Autowired
    FileController fileController;

    @GetMapping("sync")
    public ResponseEntity insertAllRecords() {
        try {
            repository.saveAll(fileController.getAllRecords());
            return new ResponseEntity("Sync ok", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
