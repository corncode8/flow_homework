package com.flow.homework.api.whitelist.usecase;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.flow.homework.api.whitelist.response.WhiteListViewResponse;
import com.flow.homework.domain.whitelist.entity.WhiteList;
import com.flow.homework.domain.whitelist.infrastructure.WhiteListJpaRepository;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class WhiteListViewUseCaseTest {

	@Autowired
	private WhiteListJpaRepository whiteListJpaRepository;

	@Autowired
	private WhiteListViewUseCase whiteListViewUseCase;

	@Container
	private static GenericContainer mySqlContainer = new MySQLContainer("mysql:8.0")
		.withReuse(true);


	@BeforeEach
	void setUp() {
		whiteListJpaRepository.saveAll(List.of(
			new WhiteList("127.0.0.1", "Test 3", LocalDateTime.now().minusDays(3), LocalDateTime.now().plusDays(3)),
			new WhiteList("127.0.0.2", "Test 2", LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(2)),
			new WhiteList("127.0.0.3", "Test 1", LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1))
		));
	}

	@DisplayName("view 테스트")
	@Test
	void viewTest() {
	    //given
		int page = 0;
		int size = 10;

	    //when
		Page<WhiteListViewResponse> result = whiteListViewUseCase.view(page, size);

		//then
		assertThat(result.getTotalElements()).isEqualTo(3);
		assertThat(result.getContent().get(0).getIpAddress()).isEqualTo("127.0.0.3");
		assertThat(result.getContent().get(1).getIpAddress()).isEqualTo("127.0.0.2");
		assertThat(result.getContent().get(2).getIpAddress()).isEqualTo("127.0.0.1");
	}
}
