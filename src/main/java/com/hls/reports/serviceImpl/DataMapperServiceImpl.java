package com.hls.reports.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.hls.reports.dto.FieldsDto;
import com.hls.reports.dto.ReportDto;
import com.hls.reports.entity.User;
import com.hls.reports.service.DataMapperService;

@Service
public class DataMapperServiceImpl implements DataMapperService {

	@Override
	public Context setData(ReportDto userList) {
		Context context = new Context();
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("users", userList);
		
		context.setVariables(data);
		
		return context;
	}
}
