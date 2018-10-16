package com.andriihlianko.testproject.util.constants;

import org.springframework.stereotype.Component;

@Component
public interface Messages {
    String ERROR_CUSTOMER_NOT_FOUND = "Customer not found with parameters such parameters";
    String INCORRECT_DATA = "You passed incorrect data.";
    String INCORRECT_DATE_FORMAT = "Incorrect date format. Pass in format yyyy";
}
