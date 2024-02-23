package com.hls.reports.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hls.reports.dto.LoginDto;
import com.hls.reports.response.ApiResponse;
import com.hls.reports.response.ErrorResponse;
import com.hls.reports.response.JwtResponse;
import com.hls.reports.serviceImpl.UserServiceImpl;
import com.hls.reports.util.ErrorCode;
import com.hls.reports.validation.RequestValidator;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/login")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
	private final RequestValidator requestValidator;
	private final UserServiceImpl userservice;
	@PostMapping
	public ResponseEntity<ApiResponse<JwtResponse>> login(@Valid @RequestBody LoginDto loginDto) {
		
		requestValidator.validateLogin(loginDto);
		JwtResponse jwtResponse = userservice.login(loginDto);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ApiResponse.of(jwtResponse));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<ErrorResponse> validationError(MethodArgumentNotValidException ex) {
		return new ResponseEntity<>(ErrorResponse.of(ErrorCode.RP_BIZ_1005.name()), HttpStatus.BAD_REQUEST);
	}
}
