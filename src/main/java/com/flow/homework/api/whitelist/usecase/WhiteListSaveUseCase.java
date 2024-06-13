package com.flow.homework.api.whitelist.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flow.homework.api.whitelist.request.SaveIpRequest;
import com.flow.homework.domain.whitelist.components.WhiteListCreator;
import com.flow.homework.domain.whitelist.components.WhiteListStore;
import com.flow.homework.domain.whitelist.components.WhiteListValidator;
import com.flow.homework.domain.whitelist.entity.WhiteList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WhiteListSaveUseCase {

	private final WhiteListStore whiteListStore;
	private final WhiteListValidator validator;

	public WhiteList save(SaveIpRequest request) {
		// 규칙 50개 제한 Validation
		validator.saveValidation();

		return whiteListStore.save(
			WhiteListCreator.create(request.getIp(), request.getDescription(), request.getStartTime(), request.getEndTime())
		);
	}
}