package com.andriihlianko.testproject.service.synchronization;

import com.andriihlianko.testproject.database.CustomerRepository;
import com.andriihlianko.testproject.model.domain.Customer;
import com.andriihlianko.testproject.service.utils.Parser;
import com.andriihlianko.testproject.util.constants.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Impl of synchronization service
 */
@Component("sync")
public class SyncServiceImpl implements SyncService {
	private final CustomerRepository repository;
	private final Parser parser;

	@Autowired
	public SyncServiceImpl(CustomerRepository repository, @Qualifier("parserCsv") Parser parser) {
		this.repository = repository;
		this.parser = parser;
	}

	@Override
	public void syncFileWithDb() {
		List<Customer> customersList = parser.parse(Customer.class, Paths.FILE_NAME);
		repository.saveAll(customersList);
	}

	@Override
	public List<Customer> syncWithFile() {
		return parser.parse(Customer.class, Paths.FILE_NAME);
	}
}
