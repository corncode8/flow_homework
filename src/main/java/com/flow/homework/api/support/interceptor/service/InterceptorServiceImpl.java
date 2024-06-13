package com.flow.homework.api.support.interceptor.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InterceptorServiceImpl implements InterceptorService{

	@Override
	public String getClientIp(HttpServletRequest request) {
		String[] headers = {"Proxy-Client-IP",
			"WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR",
			"X-Real-IP", "X-RealIP", "REMOTE_ADDR"};
		String ip = request.getHeader("X-Forwarded-For");

		for (String header : headers) {
			if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader(header);
			}
		}

		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		if(ip.equals("0:0:0:0:0:0:0:1")){
			ip = "127.0.0.1";
		}

		return ip;
	}
}
