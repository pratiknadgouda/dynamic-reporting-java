package com.hls.reports.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.hls.reports.dto.FieldsDto;
import com.hls.reports.dto.ReportDto;
import com.hls.reports.entity.User;
import com.hls.reports.service.DataMapperService;
import com.hls.reports.service.PDFConvertorService;
import com.hls.reports.serviceImpl.UserServiceImpl;
import com.hls.reports.validation.RequestValidator;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/generate")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DocumentController {
	
	
	@Autowired
	private PDFConvertorService pdfConvertorService;
	
	@Autowired
	private SpringTemplateEngine springTemplateEngine;
	
	@Autowired
	private DataMapperService dataMapperService;
	
	@GetMapping(value = "/document")
	public String generateDocument(@RequestBody ReportDto userList,@RequestHeader("Authorization") String authHeader) {
		String finalHtml = null;
		System.out.println(userList.toString());
		Context dataContext = dataMapperService.setData(userList);
		
		finalHtml = springTemplateEngine.process("reportTemplate1", dataContext);
		System.out.println(finalHtml);
		pdfConvertorService.htmlToPdfConvertor(finalHtml);
		
		return "Success";
	}
}
