package com.flow.homework.domain.whitelist.components;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.flow.homework.domain.whitelist.entity.WhiteList;

@Component
public class WhiteListCreator {

	public static WhiteList create(String ip, String description, LocalDateTime startTime, LocalDateTime endTime) {
		return WhiteList.create(ip, description, startTime, endTime);
	}

}
