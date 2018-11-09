package com.andriihlianko.testproject.controller;

import com.andriihlianko.testproject.service.synchronization.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/data")
public class SyncController {
	private final SyncService syncService;

	@Autowired
	public SyncController(@Qualifier("sync") SyncService syncService) {
		this.syncService = syncService;
	}

	/**
	 * Synchronize data from file to db.
	 *
	 * @return ResponseEntity
	 */
	@GetMapping("sync")
	public ResponseEntity<String> insertAllRecords() {
		try {
			syncService.syncFileWithDb();
			return new ResponseEntity<>("Sync ok", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
