package com.hls.reports.response;



import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Builder.Default;

@Data
@AllArgsConstructor
@Builder
public class JwtResponse {

	private String	access_token;
	private Long	expires_in;
	@Default
	private String	token_type	= "Bearer";
}