package com.flow.homework.domain.whitelist.components;

import org.springframework.stereotype.Component;

import com.flow.homework.domain.whitelist.entity.WhiteList;
import com.flow.homework.domain.whitelist.repository.WhiteListStoreRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WhiteListStore {

	private final WhiteListStoreRepository whiteListStoreRepository;

	public WhiteList save(WhiteList whiteList) {
		return whiteListStoreRepository.save(whiteList);
	}
}
