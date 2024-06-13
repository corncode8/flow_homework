package com.flow.homework.api.whitelist.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.flow.homework.api.whitelist.request.SaveIpRequest;
import com.flow.homework.domain.whitelist.entity.WhiteList;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class WhiteListSaveUseCaseTest {

	@Autowired
	private WhiteListSaveUseCase whiteListSaveUseCase;

	@Container
	private static GenericContainer mySqlContainer = new MySQLContainer("mysql:8.0")
		.withReuse(true);

	@DisplayName("save 테스트")
	@Test
	void saveTest() {
	    //given
		SaveIpRequest request = new SaveIpRequest(
			"127.0.0.1", "test", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
		);

	    //when
		WhiteList result = whiteListSaveUseCase.save(request);

		//then
		assertNotNull(result);
		assertEquals(request.getIp(), result.getIpAddress());
		assertEquals(request.getDescription(), result.getDescription());
	}
}
