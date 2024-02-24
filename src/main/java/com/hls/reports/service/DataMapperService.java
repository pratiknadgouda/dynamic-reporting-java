package com.hls.reports.service;

import org.thymeleaf.context.Context;

import com.hls.reports.dto.ReportDto;

public interface DataMapperService {
	
	public Context setData(ReportDto userList);
}
