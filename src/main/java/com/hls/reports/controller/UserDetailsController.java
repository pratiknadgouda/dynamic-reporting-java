package com.hls.reports.controller;

import java.util.List;

import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hls.reports.dto.DoctorDto;
import com.hls.reports.response.ApiResponse;
import com.hls.reports.service.UserDetailsService;
import com.hls.reports.serviceImpl.JWTServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/userDetails/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserDetailsController {

	private final UserDetailsService userDetailsService;
	private final JWTServiceImpl jwtService;

	@GetMapping
	public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String authHeader) {

		String token = authHeader.substring(7);
		String email = jwtService.getEmail(token);
		String role = jwtService.getRole(token);

		if (role.equalsIgnoreCase("DOCTOR"))
			return ResponseEntity.status(HttpStatus.OK)
					.body(ApiResponse.of(userDetailsService.getDoctorDetails(email)));
		else if (role.equalsIgnoreCase("PATIENT"))
			return ResponseEntity.status(HttpStatus.OK)
					.body(ApiResponse.of(userDetailsService.getPatientDetails(email)));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.of(null));

	}

	@GetMapping("reportData/")
	public ResponseEntity<?> getReportData(@RequestHeader("Authorization") String authHeader) {

		String token = authHeader.substring(7);
		String email = jwtService.getEmail(token);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.of(userDetailsService.getUserReport(email)));

	}

	@GetMapping("reportTemplates/")
	public ResponseEntity<?> getReportTemplates(@RequestHeader("Authorization") String authHeader) {

		String token = authHeader.substring(7);
		String email = jwtService.getEmail(token);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.of(userDetailsService.getAllTemplates()));

	}

}
