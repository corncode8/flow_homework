package com.flow.homework.api.support.exceptions;

import com.flow.homework.api.support.response.BaseResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {
	private BaseResponseStatus status;

	public BaseException(BaseResponseStatus status) {
		super(status.getMessage());
		this.status = status;
	}
}
