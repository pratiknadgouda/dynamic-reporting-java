package com.hls.reports.exceptions;

import java.util.Arrays;
import java.util.List;

import com.hls.reports.response.ValidationError;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
	private String errorCode;
	private String errorMessage;
	private static final long		serialVersionUID	= -6410270417217707464L;
	private List<ValidationError>	validationErrors;


	public ValidationException(String errorCode, List<ValidationError> validationErrors) {
		super(errorCode);
		this.errorCode = errorCode;
		this.errorMessage = "Validation Error";	
		this.validationErrors = validationErrors;
	}

	public ValidationException(String errorCode, ValidationError validationError) {
		this(errorCode, Arrays.asList(validationError));
	}
}
