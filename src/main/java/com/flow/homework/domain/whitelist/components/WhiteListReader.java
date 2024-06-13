package com.flow.homework.domain.whitelist.components;

import static com.flow.homework.api.support.response.BaseResponseStatus.NOT_FIND_WHITELIST;

import org.springframework.stereotype.Component;

import com.flow.homework.api.support.exceptions.BaseException;
import com.flow.homework.domain.whitelist.entity.WhiteList;
import com.flow.homework.domain.whitelist.repository.WhiteListReaderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WhiteListReader {

	private final WhiteListReaderRepository whiteListReaderRepository;

	public WhiteList findWhiteList(Long id) {
		return whiteListReaderRepository.findWhiteList(id)
			.orElseThrow(() -> new BaseException(NOT_FIND_WHITELIST));
	}
}
