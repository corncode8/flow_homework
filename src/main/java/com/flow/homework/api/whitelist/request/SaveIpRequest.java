package com.flow.homework.api.whitelist.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveIpRequest {

	private String ip;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
