package com.flow.homework.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.flow.homework.domain.common.DataService;

@SpringBootTest
@ActiveProfiles("data")
public class dymmydata {

	@Autowired
	private DataService dataService;

	@Test
	public void testInsertDummyData() {
		dataService.insertDummyData(1000000);
	}
}
