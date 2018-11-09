package com.andriihlianko.testproject.controller;

import com.andriihlianko.testproject.database.CustomerRepository;
import com.andriihlianko.testproject.exception.IncorrectDataException;
import com.andriihlianko.testproject.exception.NotFoundException;
import com.andriihlianko.testproject.model.domain.Customer;
import com.andriihlianko.testproject.service.filter.FilterServiceDbImpl;
import com.andriihlianko.testproject.service.filter.FilterServiceFileImpl;
import com.andriihlianko.testproject.service.synchronization.SyncService;
import com.andriihlianko.testproject.util.constants.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "api/{source}")
public class CustomersPresenterController {
	private final CustomerRepository repository;
	private final SyncService syncService;
	private List<Customer> customersList = new ArrayList<>();

	@Autowired
	public CustomersPresenterController(CustomerRepository repository, SyncService syncService) {
		this.repository = repository;
		this.syncService = syncService;
	}

	@GetMapping("/")
	public List<Customer> getAllRecords(@PathVariable("source") String source) {
		if (customersList.isEmpty()) {
			switch (source) {
				case "db": {
					if (repository.count() == 0) {
						try {
							syncService.syncFileWithDb();
						} catch (Exception e) {
							throw new IncorrectDataException("Can't sync values to db.");
						}
					}
					customersList = repository.findAll();
					break;
				}
				case "file": {
					customersList = syncService.syncWithFile();
					break;
				}
			}
		}
		return customersList;
	}

	@GetMapping
	public List<Customer> getCustomersByGenderByYearByState(@PathVariable("source") String source, @RequestParam(value = "gender", required = false) String gender,
															@RequestParam(value = "year", required = false) String year,
															@RequestParam(value = "state", required = false) String[] state) throws NotFoundException {

		List<String> states = new ArrayList<>();
		List<Customer> result = new ArrayList<>();
		if (state != null) {
			states = Arrays.asList(state);
		}
		switch (source) {
			case "db": {
				if (repository.count() == 0) {
					try {
						syncService.syncFileWithDb();
					} catch (Exception e) {
						throw new IncorrectDataException("Can't sync values to db.");
					}
				}
				FilterServiceDbImpl filter = new FilterServiceDbImpl(repository);
				try {
					result = filter.filter(gender, year, states);
				} catch (NumberFormatException e) {
					throw new IncorrectDataException(Messages.INCORRECT_DATA);
				}
				break;
			}
			case "file": {
				if (customersList.isEmpty()) {
					customersList = syncService.syncWithFile();
				}
				FilterServiceFileImpl filter = new FilterServiceFileImpl();
				try {
					result = filter.filter(customersList, gender, year, states);
				} catch (NumberFormatException e) {
					throw new IncorrectDataException(Messages.INCORRECT_DATA);
				}
			}

		}
		if (result.size() == 0) {
			throw new NotFoundException(Messages.ERROR_CUSTOMER_NOT_FOUND);
		}
		return result;
	}
}
