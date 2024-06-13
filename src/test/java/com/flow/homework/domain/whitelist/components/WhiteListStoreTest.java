package com.flow.homework.domain.whitelist.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flow.homework.domain.whitelist.entity.WhiteList;
import com.flow.homework.domain.whitelist.repository.WhiteListStoreRepository;

@ExtendWith(MockitoExtension.class)
public class WhiteListStoreTest {

	@Mock
	private WhiteListStoreRepository whiteListStoreRepository;

	@InjectMocks
	private WhiteListStore whiteListStore;

	@DisplayName("save 테스트")
	@Test
	void saveTest() {
	    //given
		WhiteList whiteList = new WhiteList(
			"127.0.0.1", "test", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
		);

		when(whiteListStoreRepository.save(whiteList)).thenReturn(whiteList);

	    //when
		WhiteList result = whiteListStore.save(whiteList);

		//then
		assertNotNull(result);
		assertEquals(result.getIpAddress(), whiteList.getIpAddress());
		assertEquals(result.getDescription(), whiteList.getDescription());
	}
}
