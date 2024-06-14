package com.flow.homework.api.whitelist.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.flow.homework.api.whitelist.request.SearchRequest;
import com.flow.homework.api.whitelist.response.WhiteListViewResponse;
import com.flow.homework.domain.whitelist.components.WhiteListStore;
import com.flow.homework.domain.whitelist.entity.WhiteList;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class WhiteListSearchUseCaseTest {

	@Autowired
	private WhiteListSearchUseCase whiteListSearchUseCase;

	@Autowired
	private WhiteListStore whiteListStore;

	@Container
	private static GenericContainer mySqlContainer = new MySQLContainer("mysql:8.0")
		.withReuse(true);

	@BeforeEach
	void setUp() {
		whiteListStore.save(new WhiteList("255.255.255.255", "test", LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1)));
	}

	@DisplayName("테스트")
	@Test
	void test() {
	    //given
		Pageable pageable = PageRequest.of(0, 10);
		SearchRequest request = new SearchRequest(
			"test", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
		);

	    //when
		Page<WhiteListViewResponse> result = whiteListSearchUseCase.search(request, pageable);

		//then
		assertNotNull(result);
		assertEquals(result.getTotalElements(), 1);
		assertEquals(result.getContent().get(0).getIpAddress(), "255.255.255.255");
	}
}
