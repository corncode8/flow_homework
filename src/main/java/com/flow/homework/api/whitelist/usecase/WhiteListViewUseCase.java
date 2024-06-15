package com.flow.homework.api.whitelist.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flow.homework.api.whitelist.response.WhiteListViewResponse;
import com.flow.homework.domain.whitelist.components.WhiteListReader;
import com.flow.homework.domain.whitelist.entity.WhiteList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WhiteListViewUseCase {

	private final WhiteListReader whiteListReader;

	public Page<WhiteListViewResponse> view(int page, int size) {
		// 등록 시간 기준 내림차순으로 나열되어야 한다.
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

		Page<WhiteList> activeWhiteLists = whiteListReader.getActiveWhiteLists(pageable);
		return activeWhiteLists.map(whiteList -> new WhiteListViewResponse(whiteList));
	}
}
