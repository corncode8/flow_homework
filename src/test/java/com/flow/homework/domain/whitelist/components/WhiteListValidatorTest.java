package com.flow.homework.domain.whitelist.components;

import static com.flow.homework.api.support.response.BaseResponseStatus.CANNOT_COMPOSED_DIGITS;
import static com.flow.homework.api.support.response.BaseResponseStatus.DESCRIPTION_TOO_LONG;
import static com.flow.homework.api.support.response.BaseResponseStatus.NOT_FIND_DESCRIPTION;
import static com.flow.homework.api.support.response.BaseResponseStatus.NOT_FIND_IP_ADDRESS;
import static com.flow.homework.api.support.response.BaseResponseStatus.NOT_FIND_START_TIME;
import static com.flow.homework.api.support.response.BaseResponseStatus.NOT_FIND_TIME;
import static com.flow.homework.api.support.response.BaseResponseStatus.WHITE_LIST_LIMIT_EXCEEDED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flow.homework.api.support.exceptions.BaseException;
import com.flow.homework.api.whitelist.request.SaveIpRequest;
import com.flow.homework.api.whitelist.request.SearchRequest;
import com.flow.homework.domain.whitelist.entity.WhiteList;
import com.flow.homework.domain.whitelist.repository.WhiteListReaderRepository;

@ExtendWith(MockitoExtension.class)
public class WhiteListValidatorTest {

	@Mock
	private WhiteListReaderRepository whiteListReaderRepository;

	@InjectMocks
	private WhiteListValidator whiteListValidator;

	@DisplayName("saveValidation 성공 테스트")
	@Test
	void saveValidationTest() {
	    //given
		SaveIpRequest request = new SaveIpRequest(
			"127.0.0.1", "test", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
		);
		when(whiteListReaderRepository.activeWhiteListNum(WhiteList.State.ACTIVE)).thenReturn(49);

	    //when & then
		whiteListValidator.saveValidation(request);
	}

	/*
	* 규칙은 최대 50개 까지 추가가 가능해야 한다.
	*/
	@DisplayName("saveValidation 실패 테스트")
	@Test
	void saveValidationFailTest() {
		//given
		SaveIpRequest request = new SaveIpRequest(
			"127.0.0.1", "test", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
		);
		when(whiteListReaderRepository.activeWhiteListNum(WhiteList.State.ACTIVE)).thenReturn(50);

		//when & then
		BaseException exception = assertThrows(BaseException.class, () -> whiteListValidator.saveValidation(request));
		assertEquals(WHITE_LIST_LIMIT_EXCEEDED.getMessage(), exception.getMessage());
	}

	/*
	 * IP Empty test
	 */
	@DisplayName("saveValidation 실패 테스트")
	@Test
	void saveValidation_IPEmptyTest() {
		//given
		SaveIpRequest request = new SaveIpRequest(
			"", "test", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
		);
		when(whiteListReaderRepository.activeWhiteListNum(WhiteList.State.ACTIVE)).thenReturn(10);

		//when & then
		BaseException exception = assertThrows(BaseException.class, () -> whiteListValidator.saveValidation(request));
		assertEquals(NOT_FIND_IP_ADDRESS.getMessage(), exception.getMessage());
	}

	/*
	 * Desctiption Empty test
	 */
	@DisplayName("saveValidation 실패 테스트")
	@Test
	void saveValidation_DesctiptionEmptyTest() {
		//given
		SaveIpRequest request = new SaveIpRequest(
			"127.0.0.1", "", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
		);
		when(whiteListReaderRepository.activeWhiteListNum(WhiteList.State.ACTIVE)).thenReturn(10);

		//when & then
		BaseException exception = assertThrows(BaseException.class, () -> whiteListValidator.saveValidation(request));
		assertEquals(NOT_FIND_DESCRIPTION.getMessage(), exception.getMessage());
	}

	/*
	 * StartTime or EndTime null test
	 */
	@DisplayName("saveValidation 실패 테스트")
	@Test
	void saveValidation_TimeNullTest() {
		//given
		SaveIpRequest request = new SaveIpRequest(
			"127.0.0.1", "test", null, null
		);
		when(whiteListReaderRepository.activeWhiteListNum(WhiteList.State.ACTIVE)).thenReturn(10);

		//when & then
		BaseException exception = assertThrows(BaseException.class, () -> whiteListValidator.saveValidation(request));
		assertEquals(NOT_FIND_TIME.getMessage(), exception.getMessage());
	}

	/*
	 * CANNOT_COMPOSED_DIGITS test
	 * 설명은 숫자로만 이루어질 수 없다.
	 */
	@DisplayName("saveValidation 실패 테스트")
	@Test
	void onlyDigitsTest() {
		//given
		SaveIpRequest request = new SaveIpRequest(
			"127.0.0.1", "123123", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
		);

		//when & then
		BaseException exception = assertThrows(BaseException.class, () -> whiteListValidator.saveValidation(request));
		assertEquals(CANNOT_COMPOSED_DIGITS.getMessage(), exception.getMessage());
	}

	/*
	 * DESCRIPTION_TOO_LONG test
	 * 설명은 20자 이상 등록될 수 없다.
	 */
	@DisplayName("saveValidation 실패 테스트")
	@Test
	void descriptionTooLongTest() {
		//given
		SaveIpRequest request = new SaveIpRequest(
			"127.0.0.1", "ajdidirkwjdirjekdirjdk", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
		);

		//when & then
		BaseException exception = assertThrows(BaseException.class, () -> whiteListValidator.saveValidation(request));
		assertEquals(DESCRIPTION_TOO_LONG.getMessage(), exception.getMessage());
	}

	@DisplayName("searchValidation 테스트")
	@Test
	void searchValidationTest() {
	    //given
		SearchRequest request = new SearchRequest(
			"test", LocalDateTime.now(), null
		);

	    //when
		SearchRequest result = whiteListValidator.searchValidation(request);

		//then
		assertNotNull(result);
		assertNotNull(result.getEndTime());
		assertEquals(request.getDescription(), result.getDescription());
	}

	/*
	 * NOT_FIND_START_TIME test
	 */
	@DisplayName("searchValidation 실패 테스트")
	@Test
	void startTimeNullTest() {
		//given
		SearchRequest request = new SearchRequest(
			"test", null, LocalDateTime.now().plusDays(1)
		);

		//when & then
		BaseException exception = assertThrows(BaseException.class, () -> whiteListValidator.searchValidation(request));
		assertEquals(NOT_FIND_START_TIME.getMessage(), exception.getMessage());
	}


}
