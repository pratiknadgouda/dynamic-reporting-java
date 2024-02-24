package com.hls.reports.util;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode{
	RP_BIZ_1005("Email Id is invalid", HttpStatus.NOT_FOUND);

	private String		error;
	private HttpStatus	httpStatus;

	ErrorCode(String error, HttpStatus httpStatus) {
		this.error = error;
		this.httpStatus = httpStatus;
	}
}
