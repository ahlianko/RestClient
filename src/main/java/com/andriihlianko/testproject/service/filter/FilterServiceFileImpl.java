package com.andriihlianko.testproject.service.filter;

import com.andriihlianko.testproject.model.domain.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("fileFilter")
public class FilterServiceFileImpl implements FilterService {

    @Override
    public List<Customer> filter(List<Customer> customersList, String gender, String year, List<String> states) throws NumberFormatException {
        return customersList.stream()
                .filter(line -> gender == null || Double.valueOf(gender) == (line.getGender()))
                .filter(line -> year == null || this.getBirthYear(line) == Integer.valueOf(year))
                .filter(line -> states.size() == 0 || states.contains(line.getStatus()))
                .collect(Collectors.toList());
    }
}
