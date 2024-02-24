package com.hls.reports.response;

import java.io.Serializable;
import java.util.List;


import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
public class ValidationError implements Serializable {
	private static final long serialVersionUID = 2919003580661730797L;
	
	private String				errorCode;
	private String              error;
	@Singular()
	private List<String>		fields;
}