package com.flow.homework.api.whitelist.usecase;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flow.homework.api.whitelist.request.DeleteIpRequest;
import com.flow.homework.domain.whitelist.components.WhiteListReader;
import com.flow.homework.domain.whitelist.entity.WhiteList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WhiteListDeleteUseCase {

	private final WhiteListReader whiteListReader;

	public Boolean delete(Long id) {
		try {
			WhiteList whiteList = whiteListReader.findWhiteList(id);
			whiteList.delete();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
}
