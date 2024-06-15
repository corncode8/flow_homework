package com.flow.homework.domain.whitelist.components;

import static com.flow.homework.api.support.response.BaseResponseStatus.NOT_FIND_WHITELIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.flow.homework.api.support.exceptions.BaseException;
import com.flow.homework.domain.whitelist.entity.WhiteList;
import com.flow.homework.domain.whitelist.repository.WhiteListReaderRepository;

@ExtendWith(MockitoExtension.class)
public class WhiteListReaderTest {

	@Mock
	private WhiteListReaderRepository whiteListReaderRepository;

	@InjectMocks
	private WhiteListReader whiteListReader;

	@DisplayName("findWhiteList 테스트")
	@Test
	void findWhiteListTest() {
	    //given
		Long testId = 1L;
		WhiteList whiteList = new WhiteList(
			"127.0.0.1", "test", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
		);

	    when(whiteListReaderRepository.findWhiteList(testId)).thenReturn(Optional.of(whiteList));

	    //when
		WhiteList result = whiteListReader.findWhiteList(testId);

		//then
		assertNotNull(result);
		assertEquals(result.getIpAddress(), whiteList.getIpAddress());
		assertEquals(result.getDescription(), whiteList.getDescription());
	}

	@DisplayName("findWhiteList 실패 테스트")
	@Test
	void findWhiteListFailTest() {
	    //given
		Long testId = 1L;
		when(whiteListReaderRepository.findWhiteList(testId)).thenReturn(Optional.empty());

		//when & then
		BaseException exception = assertThrows(BaseException.class, () -> whiteListReader.findWhiteList(testId));
		assertEquals(NOT_FIND_WHITELIST.getMessage(), exception.getMessage());
	}

	@DisplayName("getActiveWhiteLists 테스트")
	@Test
	void getActiveWhiteListsTest() {
	    //given
		Pageable pageable = PageRequest.of(0, 10);

		List<WhiteList> whiteLists = Arrays.asList(
			new WhiteList(
				"127.0.0.1", "test", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
			),
			new WhiteList(
				"127.0.1.1", "Test1", LocalDateTime.now(), LocalDateTime.now().plusDays(2)
			),
			new WhiteList(
				"127.1.1.1", "Test2", LocalDateTime.now(), LocalDateTime.now().plusDays(3)
			)
		);

		Page<WhiteList> whiteListPage = new PageImpl<>(whiteLists, pageable, whiteLists.size());
		when(whiteListReaderRepository.getActiveWhiteLists(eq(WhiteList.State.ACTIVE), any(Pageable.class))).thenReturn(whiteListPage);

		//when
		Page<WhiteList> result = whiteListReaderRepository.getActiveWhiteLists(WhiteList.State.ACTIVE, pageable);

	    //then
		assertNotNull(result);
		assertEquals(3, result.getTotalElements());
		assertEquals("127.0.0.1", result.getContent().get(0).getIpAddress());
		assertEquals("127.0.1.1", result.getContent().get(1).getIpAddress());
		assertEquals("127.1.1.1", result.getContent().get(2).getIpAddress());
	}
}
