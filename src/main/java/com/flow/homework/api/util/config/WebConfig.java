package com.flow.homework.api.util.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.flow.homework.api.support.interceptor.WhiteListInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final WhiteListInterceptor whiteListInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(whiteListInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/static/**")
			.excludePathPatterns("/error/**");
	}
}
