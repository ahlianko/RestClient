package com.andriihlianko.testproject.database;

import com.andriihlianko.testproject.model.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByGenderEquals(double gender);

    List<Customer> findAllByDateOfBirthEquals(String dateOfBirth);

    List<Customer> findAllByStatusEquals(String status);

    List<Customer> findAllByGenderAndDateOfBirthAndStatus(double gender, String dateOfBirth, String status);

    Stream<Customer> streamAllByGender(double gender);
}
