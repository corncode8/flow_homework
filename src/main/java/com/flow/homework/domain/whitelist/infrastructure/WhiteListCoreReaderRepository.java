package com.flow.homework.domain.whitelist.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.flow.homework.domain.whitelist.entity.WhiteList;
import com.flow.homework.domain.whitelist.repository.WhiteListReaderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class WhiteListCoreReaderRepository implements WhiteListReaderRepository {

	private final WhiteListJpaRepository whiteListJpaRepository;

	@Override
	public Optional<WhiteList> findWhiteList(Long id) {
		return whiteListJpaRepository.findById(id);
	}

	@Override
	public int activeWhiteListNum(WhiteList.State state) {
		return whiteListJpaRepository.activeWhiteListNum(state);
	}
}
