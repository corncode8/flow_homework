package com.flow.homework.api.support.interceptor.service;

import javax.servlet.http.HttpServletRequest;

public interface InterceptorService {

	/*
	유저의 IP 가져오기
	@param request
	@return String
	*/
	String getClientIp(HttpServletRequest request);
}
