package com.andriihlianko.testproject.service.filter;

import com.andriihlianko.testproject.exception.IncorrectDataException;
import com.andriihlianko.testproject.model.domain.Customer;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public interface FilterService {
    List<Customer> filter (List<Customer> customersList, String gender, String year, List<String> states) throws NumberFormatException;
    default int getBirthYear(Customer customer) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        try {
            Date date = sdf.parse(customer.getDateOfBirth());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR);
        } catch (ParseException e){
            throw new IncorrectDataException("Incorrect date format. Follow dd/mm/yyyy");
        }

    }
}
