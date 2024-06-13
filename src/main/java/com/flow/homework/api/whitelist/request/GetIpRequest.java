package com.flow.homework.api.whitelist.request;

import java.time.LocalDateTime;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetIpRequest {

	@Nullable
	public String description;

	@Nullable
	public LocalDateTime startTime;
	@Nullable
	public LocalDateTime endTime;
}
