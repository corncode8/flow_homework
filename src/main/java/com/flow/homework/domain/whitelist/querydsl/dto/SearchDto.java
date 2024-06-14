package com.flow.homework.domain.whitelist.querydsl.dto;

import java.time.LocalDateTime;

import org.springframework.lang.Nullable;

import com.flow.homework.api.whitelist.request.SearchRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {

	@Nullable
	private String description;

	@Nullable
	private LocalDateTime startTime;

	@Nullable
	private LocalDateTime endTime;

	public SearchDto(SearchRequest request) {
		this.description = request.getDescription();
		this.startTime = request.getStartTime();
		this.endTime = request.getEndTime();
	}
}
