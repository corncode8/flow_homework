package com.flow.homework.domain.whitelist.components;

import static com.flow.homework.api.support.response.BaseResponseStatus.WHITE_LIST_LIMIT_EXCEEDED;

import org.springframework.stereotype.Component;

import com.flow.homework.api.support.exceptions.BaseException;
import com.flow.homework.domain.whitelist.entity.WhiteList;
import com.flow.homework.domain.whitelist.repository.WhiteListReaderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WhiteListValidator {

	private final WhiteListReaderRepository whiteListReaderRepository;

	public void saveValidation() {
		int num = whiteListReaderRepository.activeWhiteListNum(WhiteList.State.ACTIVE);

		if (num >= 50) {
			throw new BaseException(WHITE_LIST_LIMIT_EXCEEDED);
		}
	}
}
