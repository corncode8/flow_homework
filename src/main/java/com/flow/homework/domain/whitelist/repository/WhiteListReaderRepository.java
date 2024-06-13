package com.flow.homework.domain.whitelist.repository;

import java.util.Optional;

import com.flow.homework.domain.whitelist.entity.WhiteList;

public interface WhiteListReaderRepository {

	Optional<WhiteList> findWhiteList(Long id);

	int activeWhiteListNum(WhiteList.State state);
}
