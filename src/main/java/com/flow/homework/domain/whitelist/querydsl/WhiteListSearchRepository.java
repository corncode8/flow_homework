package com.flow.homework.domain.whitelist.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.flow.homework.api.whitelist.response.WhiteListViewResponse;
import com.flow.homework.domain.whitelist.entity.WhiteList;
import com.flow.homework.domain.whitelist.querydsl.dto.SearchDto;

public interface WhiteListSearchRepository {

	Page<WhiteListViewResponse> searchWhiteList(SearchDto searchDto, Pageable pageable);
}
