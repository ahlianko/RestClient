package com.andriihlianko.testproject.service.utils;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Parser {
	<T> List<T> parse(Class<T> type, String fileName);
}
