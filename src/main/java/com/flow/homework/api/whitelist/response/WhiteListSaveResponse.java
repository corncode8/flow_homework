package com.flow.homework.api.whitelist.response;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flow.homework.domain.whitelist.entity.WhiteList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhiteListSaveResponse {

	private String ipAddress;
	private String description;


	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public WhiteListSaveResponse(WhiteList whiteList) {
		this.ipAddress = whiteList.getIpAddress();
		this.description = whiteList.getDescription();
		this.startTime = whiteList.getStartTime();
		this.endTime = whiteList.getEndTime();
	}
}
