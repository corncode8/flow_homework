package com.flow.homework.api.support.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {

	/**
	 * 200 : 요청 성공
	 */
	SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),

	/**
	 * 400 : Request, Response 오류
	 */

	NOT_FIND_WHITELIST(false, HttpStatus.NOT_FOUND.value(), "일치하는 WhiteList가 없습니다."),
	DELETED_WHITELIST(false, HttpStatus.NOT_FOUND.value(), "삭제된 WHiteList 입니다."),
	WHITE_LIST_LIMIT_EXCEEDED(false, HttpStatus.BAD_REQUEST.value(), "50개 이상 등록할 수 없습니다."),

	INVALID_RESERVATION(false, HttpStatus.NOT_FOUND.value(), "일치하는 예약 정보가 없습니다."),
	EMPTY_SEAT_RESERVATION(false, HttpStatus.NOT_FOUND.value(), "예약 가능한 좌석이 없습니다."),
	RESERVED_SEAT(false, HttpStatus.BAD_REQUEST.value(), "예약된 좌석입니다."),
	FAIL_RESERVAION_SEAT(false, HttpStatus.BAD_REQUEST.value(), "예약에 실패하였습니다."),
	UPDATE_ERROR_RESERVAION_STATUS(false, HttpStatus.BAD_REQUEST.value(), "예약 상태 변경에 실패하였습니다."),

	INVALID_DATE(false, HttpStatus.BAD_REQUEST.value(), "형식에 맞지 않는 날짜 양식입니다."),
	NOT_FOUND_CONCERT_OPTION(false, HttpStatus.NOT_FOUND.value(), "찾을 수 없는 콘서트 일정입니다."),

	NOT_FOUND_PAYMENT(false, HttpStatus.NOT_FOUND.value(), "찾을 수 없는 결제내역 입니다."),
	PAY_INVALID_SEAT(false, HttpStatus.BAD_REQUEST.value(), "결제: 유효하지 않은 좌석입니다."),
	PAY_EXPIRED_SEAT(false, HttpStatus.BAD_REQUEST.value(), "결제: 만료된 좌석입니다."),
	NOT_FOUND_EVENT_HISTORY(false, HttpStatus.NOT_FOUND.value(), "찾을 수 없는 EventHistory 입니다."),

	/**
	 * 500 :  Database, Server 오류
	 */
	DATABASE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
	DATABASE_EMPTY(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "저장된 데이터가 없습니다."),
	SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다."),

	UNEXPECTED_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");

	private final boolean isSuccess;
	private final int code;
	private final String message;

	private BaseResponseStatus(boolean isSuccess, int code, String message) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
	}
}
