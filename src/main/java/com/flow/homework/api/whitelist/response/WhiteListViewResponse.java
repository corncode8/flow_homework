package com.flow.homework.api.whitelist.response;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flow.homework.domain.whitelist.entity.WhiteList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WhiteListViewResponse {
	private Long whiteListId;
	private String ipAddress;
	private String description;

	private ZonedDateTime startTime;
	private ZonedDateTime endTime;

	public WhiteListViewResponse(Long whiteListId, String ipAddress, String description, LocalDateTime startTime, LocalDateTime endTime) {
		this.whiteListId = whiteListId;
		this.ipAddress = ipAddress;
		this.description = description;
		this.startTime = startTime.atZone(ZoneOffset.UTC);
		this.endTime = endTime.atZone(ZoneOffset.UTC);
	}

	public WhiteListViewResponse(WhiteList whiteList) {
		this.whiteListId = whiteList.getId();
		this.ipAddress = whiteList.getIpAddress();
		this.description = whiteList.getDescription();
		this.startTime = whiteList.getStartTime().atZone(ZoneOffset.UTC);
		this.endTime = whiteList.getEndTime().atZone(ZoneOffset.UTC);
	}
}
