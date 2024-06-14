package com.flow.homework.domain.whitelist.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.flow.homework.domain.whitelist.entity.WhiteList;

public interface WhiteListReaderRepository {

	Optional<WhiteList> findWhiteList(Long id);

	int activeWhiteListNum(WhiteList.State state);

	Page<WhiteList> getActiveWhiteLists(WhiteList.State status, Pageable pageable);

}
