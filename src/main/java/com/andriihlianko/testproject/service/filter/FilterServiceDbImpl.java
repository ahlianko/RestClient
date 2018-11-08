package com.andriihlianko.testproject.service.filter;

import com.andriihlianko.testproject.database.CustomerRepository;
import com.andriihlianko.testproject.model.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("dbFilter")
public class FilterServiceDbImpl implements FilterService {
    private final CustomerRepository customerRepository;

    @Autowired
    public FilterServiceDbImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> filter(List<Customer> customersList, String gender, String year, List<String> states) throws NumberFormatException {
        return customerRepository.findAll().stream().filter(line -> gender == null || Double.valueOf(gender) == (line.getGender()))
                .filter(line -> year == null || this.getBirthYear(line) == Integer.valueOf(year))
                .filter(line -> states.size() == 0 || states.contains(line.getStatus()))
                .collect(Collectors.toList());
    }
}
