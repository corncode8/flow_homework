package com.flow.homework.api.whitelist.request;

import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchRequest {

	@Size(max = 20, message = "설명은 최대 20자까지 입력할 수 있습니다.")
	@Nullable
	private String description;

	@Nullable
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime startTime;

	@Nullable
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime endTime;
}
