package com.flow.homework.api.interceptor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.flow.homework.api.support.interceptor.service.InterceptorServiceImpl;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class InterceptorServiceImplTest {

	@Autowired
	private InterceptorServiceImpl interceptorServiceImpl;

	@Container
	private static GenericContainer mySqlContainer = new MySQLContainer("mysql:8.0")
		.withReuse(true);

	@DisplayName("getClientIp 테스트")
	@Test
	void testGetClientIp() {
		MockHttpServletRequest request = new MockHttpServletRequest();

		// "X-Forwarded-For" header
		request.addHeader("X-Forwarded-For", "192.168.0.1");
		String ip = interceptorServiceImpl.getClientIp(request);
		assertEquals("192.168.0.1", ip);

		request = new MockHttpServletRequest();
		request.addHeader("Proxy-Client-IP", "192.168.0.2");
		ip = interceptorServiceImpl.getClientIp(request);
		assertEquals("192.168.0.2", ip);

		request = new MockHttpServletRequest();
		request.setRemoteAddr("192.168.0.3");
		ip = interceptorServiceImpl.getClientIp(request);
		assertEquals("192.168.0.3", ip);

		// IPv6 address
		request = new MockHttpServletRequest();
		request.setRemoteAddr("0:0:0:0:0:0:0:1");
		ip = interceptorServiceImpl.getClientIp(request);
		assertEquals("127.0.0.1", ip);

		// unknown headers
		request = new MockHttpServletRequest();
		request.addHeader("UNKNOWN_HEADER", "unknown");
		request.setRemoteAddr("192.168.0.4");
		ip = interceptorServiceImpl.getClientIp(request);
		assertEquals("192.168.0.4", ip);
	}

}
