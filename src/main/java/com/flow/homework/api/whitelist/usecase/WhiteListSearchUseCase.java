package com.flow.homework.api.whitelist.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flow.homework.api.whitelist.request.SearchRequest;
import com.flow.homework.api.whitelist.response.WhiteListViewResponse;
import com.flow.homework.domain.whitelist.components.WhiteListReader;
import com.flow.homework.domain.whitelist.components.WhiteListValidator;
import com.flow.homework.domain.whitelist.entity.WhiteList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WhiteListSearchUseCase {

	private final WhiteListValidator validator;
	private final WhiteListReader whiteListReader;

	public Page<WhiteListViewResponse> search(SearchRequest request, Pageable pageable) {

		// 검색 조건 검증
		SearchRequest searchRequest = validator.searchValidation(request);

		return whiteListReader.searchWhiteList(searchRequest, pageable);
	}
}
