package com.flow.homework.domain.whitelist.infrastructure;

import org.springframework.stereotype.Repository;

import com.flow.homework.domain.whitelist.entity.WhiteList;
import com.flow.homework.domain.whitelist.repository.WhiteListStoreRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class WhiteListCoreStoreRepository implements WhiteListStoreRepository {

	private final WhiteListJpaRepository whiteListJpaRepository;

	@Override
	public WhiteList save(WhiteList whiteList) {
		return whiteListJpaRepository.save(whiteList);
	}
}
