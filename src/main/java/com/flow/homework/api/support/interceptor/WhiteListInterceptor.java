package com.flow.homework.api.support.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.flow.homework.api.support.interceptor.service.InterceptorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WhiteListInterceptor implements HandlerInterceptor {
	private final InterceptorService interceptorService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {

		String clientIp = interceptorService.getClientIp(request);
		request.setAttribute("clientIp", clientIp);

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
