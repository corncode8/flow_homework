package com.flow.homework.domain.whitelist.components;

import static com.flow.homework.api.support.response.BaseResponseStatus.WHITE_LIST_LIMIT_EXCEEDED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flow.homework.api.support.exceptions.BaseException;
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
		when(whiteListReaderRepository.activeWhiteListNum(WhiteList.State.ACTIVE)).thenReturn(49);

	    //when & then
		whiteListValidator.saveValidation();
	}

	/*
	* 규칙은 최대 50개 까지 추가가 가능해야 한다.
	*/
	@DisplayName("saveValidation 실패 테스트")
	@Test
	void saveValidationFailTest() {
		//given
		when(whiteListReaderRepository.activeWhiteListNum(WhiteList.State.ACTIVE)).thenReturn(50);

		//when & then
		BaseException exception = assertThrows(BaseException.class, () -> whiteListValidator.saveValidation());
		assertEquals(WHITE_LIST_LIMIT_EXCEEDED.getMessage(), exception.getMessage());
	}

}
