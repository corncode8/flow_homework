package com.flow.homework.api.whitelist.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flow.homework.api.whitelist.request.SaveIpRequest;
import com.flow.homework.api.whitelist.request.SearchRequest;
import com.flow.homework.domain.whitelist.components.WhiteListStore;
import com.flow.homework.domain.whitelist.entity.WhiteList;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WhiteListControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WhiteListStore whiteListStore;

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

	@DisplayName("whiteListView 테스트")
	@Test
	void whiteListViewTest() throws Exception{
	    mockMvc.perform(get("/")
			.param("page", "0")
			.param("size", "10"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("main"))
			.andExpect(model().attributeExists("whiteList"));
	}

	@DisplayName("getIp 테스트")
	@Test
	void getIpTest() throws Exception{
	    //given
		String clientIp = "127.0.0.1";

		mockMvc.perform(get("/ip")
				.requestAttr("clientIp", clientIp))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
			.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("요청에 성공하였습니다."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.result.ip").value(clientIp));

	}

	@DisplayName("save 테스트")
	@Test
	void saveTest() throws Exception{
	    //given
		SaveIpRequest request = new SaveIpRequest(
			"127.0.0.1", "test", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
		);

	    mockMvc.perform(post("/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request))
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.isSuccess").value(true))
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.message").value("요청에 성공하였습니다."))
			.andExpect(jsonPath("$.result.ipAddress").value(request.getIp()))
			.andExpect(jsonPath("$.result.description").value(request.getDescription()));

	}

	@DisplayName("search 테스트")
	@Test
	void searchTest() throws Exception{
	    //given
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

		SearchRequest request = new SearchRequest("test", LocalDateTime.now(), LocalDateTime.now().plusDays(1));

		mockMvc.perform(get("/search")
				.param("description", request.getDescription())
				.param("startTime", request.getStartTime().format(formatter))
				.param("endTime", request.getEndTime().format(formatter))
				.param("page", "0")
				.param("size", "10"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("main"))
			.andExpect(model().attributeExists("whiteList"))
			.andExpect(model().attributeExists("currentPage"))
			.andExpect(model().attributeExists("totalPages"));
	}

	@DisplayName("delete 테스트")
	@Test
	void deleteTest() throws Exception{
	    //given
		Long testId = 1L;

		mockMvc.perform(delete("/delete/{id}", testId))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.isSuccess").value(true))
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.message").value("요청에 성공하였습니다."))
			.andExpect(jsonPath("$.result").value(true));
	}


}
