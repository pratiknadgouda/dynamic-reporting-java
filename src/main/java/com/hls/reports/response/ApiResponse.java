package com.hls.reports.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> implements Serializable {
	private static final long serialVersionUID = -6182303073959635193L;
	private transient T data;
	
	@JsonCreator
	public static <T> ApiResponse<T> of(T data) {
		return new ApiResponse<T>(data);
	}
}
