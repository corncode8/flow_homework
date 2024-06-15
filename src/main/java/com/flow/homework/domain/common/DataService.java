package com.flow.homework.domain.common;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DataService {
	// Dummy Data 삽입
	private static final int BATCH_SIZE = 1000;
	private final JdbcTemplate jdbcTemplate;

	public void insertDummyData(int totalRecords) {
		List<Object[]> batchArgs = new ArrayList<>();

		for (int i = 0; i < totalRecords; i++) {
			Object[] record = new Object[] {
				new Timestamp(System.currentTimeMillis()), // createdAt
				new Timestamp(System.currentTimeMillis()), // updatedAt
				"Test_" + i, // description
				new Timestamp(System.currentTimeMillis() + (i * 60000)), // end_time
				"192.168.0." + (i % 256), // ip_address
				new Timestamp(System.currentTimeMillis() - (i * 60000)), // start_time
				"DELETE" // status
			};

			batchArgs.add(record);

			if (batchArgs.size() == BATCH_SIZE) {
				jdbcTemplate.batchUpdate(
					"INSERT INTO WhiteList (createdAt, updatedAt, description, end_time, ip_address, start_time, status) VALUES (?, ?, ?, ?, ?, ?, ?)",
					batchArgs
				);
				batchArgs.clear();
			}
		}

		// Insert remaining records
		if (!batchArgs.isEmpty()) {
			jdbcTemplate.batchUpdate(
				"INSERT INTO WhiteList (createdAt, updatedAt, description, end_time, ip_address, start_time, status) VALUES (?, ?, ?, ?, ?, ?, ?)",
				batchArgs
			);
		}
	}
}
