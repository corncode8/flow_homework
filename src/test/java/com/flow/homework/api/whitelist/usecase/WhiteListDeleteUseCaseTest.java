package com.flow.homework.api.whitelist.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.flow.homework.api.whitelist.request.DeleteIpRequest;
import com.flow.homework.domain.whitelist.components.WhiteListReader;
import com.flow.homework.domain.whitelist.components.WhiteListStore;
import com.flow.homework.domain.whitelist.entity.WhiteList;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class WhiteListDeleteUseCaseTest {

	@Autowired
	private WhiteListStore whiteListStore;

	@Autowired
	private WhiteListReader whiteListReader;

	@Autowired
	private WhiteListDeleteUseCase whiteListDeleteUseCase;

	@Container
	private static GenericContainer mySqlContainer = new MySQLContainer("mysql:8.0")
		.withReuse(true);

	@BeforeEach
	void setUp() {
		WhiteList whiteList = new WhiteList(
			"127.0.0.1", "test", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
		);
		whiteListStore.save(whiteList);
	}

	@DisplayName("delete 테스트")
	@Test
	void deleteTest() {
	    //given
		Long testId = 1L;

	    //when
		whiteListDeleteUseCase.delete(testId);

	    //then
		WhiteList result = whiteListReader.findWhiteList(testId);

		// status가 DELETE로 변경되어야 한다.
		assertEquals(WhiteList.State.DELETE, result.getStatus());
	}
}
