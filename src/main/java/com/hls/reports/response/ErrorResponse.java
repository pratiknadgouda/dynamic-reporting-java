package com.hls.reports.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


import lombok.Getter;
import lombok.ToString;

@Getter
@JsonInclude(Include.NON_NULL)
@ToString
public class ErrorResponse implements Serializable {
	private static final long serialVersionUID = -5657959594791966966L;
	
	private String					errorCode;
	private String					errorMessage;
	private List<ValidationError>	validationErrors;

	private ErrorResponse(String errorCode) {
		this.errorCode = errorCode;
	}
	
	private ErrorResponse(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	private ErrorResponse(String errorCode, List<ValidationError> validationErrors) {
		this.errorCode = errorCode;
		this.validationErrors = validationErrors;
	}

	@JsonCreator
	public static ErrorResponse of(String errorCode) {
		return new ErrorResponse(errorCode);
	}
	public static ErrorResponse of(String errorCode, String errorMessage) {
		return new ErrorResponse(errorCode, errorMessage);
	}

	public static ErrorResponse of(String errorCode, List<ValidationError> validationErrors) {
		return new ErrorResponse(errorCode, validationErrors);
	}
}