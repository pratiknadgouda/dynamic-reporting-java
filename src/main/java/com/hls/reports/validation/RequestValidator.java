package com.hls.reports.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hls.reports.dto.LoginDto;
import com.hls.reports.exceptions.ValidationException;
import com.hls.reports.response.ValidationError;


import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
@Component
public class RequestValidator {
	public void validateLogin(@Valid LoginDto loginDto) {
		
		List<ValidationError> validationErrors = new ArrayList<>();
		if (StringUtils.isBlank(loginDto.getEmail())) {
			buildError("email", "Email Id is required", validationErrors);
		}
		if (!validationErrors.isEmpty()) {
			throw new ValidationException("INVALID_INPUT", validationErrors);
		}
		throwValidationException(validationErrors);
	}

	private void throwValidationException(List<ValidationError> validationErrors) {
		if (!(validationErrors.isEmpty())) {
			throw new ValidationException("BAD REQUEST", validationErrors);
		}
	}
	private void buildError(String fieldKey,String errorCode, List<ValidationError> validationErrors) {
		validationErrors.add(ValidationError.builder().field(fieldKey).errorCode(errorCode).error(errorCode).build());
	}
}
