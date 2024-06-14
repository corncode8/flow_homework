package com.flow.homework.domain.whitelist.components;

import static com.flow.homework.api.support.response.BaseResponseStatus.NOT_FIND_WHITELIST;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.flow.homework.api.support.exceptions.BaseException;
import com.flow.homework.api.whitelist.request.SearchRequest;
import com.flow.homework.api.whitelist.response.WhiteListViewResponse;
import com.flow.homework.domain.whitelist.entity.WhiteList;
import com.flow.homework.domain.whitelist.querydsl.WhiteListSearchRepository;
import com.flow.homework.domain.whitelist.querydsl.dto.SearchDto;
import com.flow.homework.domain.whitelist.repository.WhiteListReaderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WhiteListReader {

	private final WhiteListReaderRepository whiteListReaderRepository;
	private final WhiteListSearchRepository whiteListSearchRepository;

	public WhiteList findWhiteList(Long id) {
		return whiteListReaderRepository.findWhiteList(id)
			.orElseThrow(() -> new BaseException(NOT_FIND_WHITELIST));
	}

	public Page<WhiteList> getActiveWhiteLists(WhiteList.State status, Pageable pageable) {
		return whiteListReaderRepository.getActiveWhiteLists(status, pageable);
	}

	public Page<WhiteListViewResponse> searchWhiteList(SearchRequest request, Pageable pageable) {
		return whiteListSearchRepository.searchWhiteList(new SearchDto(request), pageable);
	}
}
