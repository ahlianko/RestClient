package com.andriihlianko.testproject.controller;

import com.andriihlianko.testproject.exception.IncorrectDataException;
import com.andriihlianko.testproject.exception.NotFoundException;
import com.andriihlianko.testproject.model.domain.Customer;
import com.andriihlianko.testproject.service.filter.FilterService;
import com.andriihlianko.testproject.service.utils.Parser;
import com.andriihlianko.testproject.util.constants.Messages;
import com.andriihlianko.testproject.util.constants.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/file")
public class FileController {
    @Autowired
    @Qualifier("fileFilter")
    private FilterService filter;
    @Autowired
    @Qualifier("parserCsv")
    private Parser parser;
    private static List<Customer> customersList = new ArrayList<>();

    @GetMapping("/")
    public List<Customer> getAllRecords() {
        customersList = parser.parse(Customer.class, Paths.FILE_NAME);
        return customersList;
    }

    @GetMapping
    public List<Customer> getCustomersByGenderByYearByState(@RequestParam(value = "gender", required = false) String gender,
                                                            @RequestParam(value = "year", required = false) String year,
                                                            @RequestParam(value = "state", required = false) String[] state) throws NotFoundException {
        if (customersList.size() == 0) {
            getAllRecords();
        }
        List<String> states = new ArrayList<>();
        if (state != null) {
            states = Arrays.asList(state);
        }
        try {
            List<Customer> result = filter.filter(customersList, gender, year, states);
            if (result.size() == 0) {
                throw new NotFoundException(Messages.ERROR_CUSTOMER_NOT_FOUND);
            }
            return result;
        } catch (NumberFormatException e) {
            throw new IncorrectDataException(Messages.INCORRECT_DATA);
        }
    }
}