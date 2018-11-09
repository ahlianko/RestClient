package com.andriihlianko.testproject.service.synchronization;

import com.andriihlianko.testproject.model.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Synchronization service
 */
@Service
public interface SyncService {
	/**
	 * Synchronize data from file to db.
	 */
	void syncFileWithDb() throws Exception;

	/**
	 * Synchronize data from file and return list.
	 *
	 * @return List<Customer>
	 */
	List<Customer> syncWithFile();
}
