package com.flow.homework.domain.whitelist.components;

import static com.flow.homework.api.support.response.BaseResponseStatus.NOT_FIND_DESCRIPTION;
import static com.flow.homework.api.support.response.BaseResponseStatus.NOT_FIND_IP_ADDRESS;
import static com.flow.homework.api.support.response.BaseResponseStatus.NOT_FIND_START_TIME;
import static com.flow.homework.api.support.response.BaseResponseStatus.NOT_FIND_TIME;
import static com.flow.homework.api.support.response.BaseResponseStatus.WHITE_LIST_LIMIT_EXCEEDED;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.flow.homework.api.support.exceptions.BaseException;
import com.flow.homework.api.whitelist.request.SaveIpRequest;
import com.flow.homework.api.whitelist.request.SearchRequest;
import com.flow.homework.domain.whitelist.entity.WhiteList;
import com.flow.homework.domain.whitelist.repository.WhiteListReaderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WhiteListValidator {

	private final WhiteListReaderRepository whiteListReaderRepository;

	public void saveValidation(SaveIpRequest request) {
		int num = whiteListReaderRepository.activeWhiteListNum(WhiteList.State.ACTIVE);

		if (num >= 50) {
			throw new BaseException(WHITE_LIST_LIMIT_EXCEEDED);
		}
		if (request.getIp().isEmpty()) {
			throw new BaseException(NOT_FIND_IP_ADDRESS);
		}
		if (request.getDescription().isEmpty()) {
			throw new BaseException(NOT_FIND_DESCRIPTION);
		}
		if (request.getStartTime() == null || request.getEndTime() == null) {
			throw new BaseException(NOT_FIND_TIME);
		}
	}

	public SearchRequest searchValidation(SearchRequest request) {
		if (request.getStartTime() == null) {
			throw new BaseException(NOT_FIND_START_TIME);
		} else if (request.getStartTime() != null && request.getEndTime() == null) {
			request.setEndTime(LocalDateTime.now());
		}

		return request;
	}
}
