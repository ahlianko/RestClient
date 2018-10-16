package com.andriihlianko.testproject.controller;

import com.andriihlianko.testproject.database.CustomerRepository;
import com.andriihlianko.testproject.exception.IncorrectDataException;
import com.andriihlianko.testproject.exception.NotFoundException;
import com.andriihlianko.testproject.model.domain.Customer;
import com.andriihlianko.testproject.service.filter.FilterService;
import com.andriihlianko.testproject.util.constants.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/db")
public class DatabaseController {
    @Autowired
    CustomerRepository repository;
    @Autowired
    SyncController syncController;
    @Autowired
    @Qualifier("dbFilter")
    FilterService filter;

    @GetMapping("/")
    public List<Customer> getAllRecords() {
        if (repository.count() == 0) {
            ResponseEntity response = syncController.insertAllRecords();
            if (!response.getStatusCode().equals(new ResponseEntity(HttpStatus.OK).getStatusCode())) {
                throw new IncorrectDataException("Can't sync values to db.");
            }
        }
        System.out.println(repository.count());
        return repository.findAll();
    }

    @GetMapping
    public List<Customer> getCustomersByGenderByYearByState(@RequestParam(value = "gender", required = false) String gender,
                                                            @RequestParam(value = "year", required = false) String year,
                                                            @RequestParam(value = "state", required = false) String[] state) throws NotFoundException {
        if (repository.count() == 0) {
            syncController.insertAllRecords();
        }
        List<String> states = new ArrayList<>();
        if (state != null) {
            states = Arrays.asList(state);
        }
        try {
            List<Customer> result = filter.filter(null, gender, year, states);
            if (result.size() == 0) {
                throw new NotFoundException(Messages.ERROR_CUSTOMER_NOT_FOUND);
            }
            return result;
        } catch (NumberFormatException e) {
            throw new IncorrectDataException(Messages.INCORRECT_DATA);
        }
    }
}
