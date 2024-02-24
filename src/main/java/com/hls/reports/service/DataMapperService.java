package com.hls.reports.service;

import org.thymeleaf.context.Context;

import com.hls.reports.dto.ReportsDto;

public interface DataMapperService {
	
	public Context setData(ReportsDto userList);
}
