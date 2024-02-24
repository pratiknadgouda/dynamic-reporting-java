package com.hls.reports.controller;

import java.io.IOException;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hls.reports.dto.ReportsDto;
import com.hls.reports.entity.ReportDetail;
import com.hls.reports.entity.ReportTemplate;
import com.hls.reports.entity.User;
import com.hls.reports.exceptions.ReportException;
import com.hls.reports.repository.ReportDetailRepository;
import com.hls.reports.repository.ReportTemplateRepository;
import com.hls.reports.repository.UserRepository;
import com.hls.reports.service.DataMapperService;
import com.hls.reports.service.PDFConvertorService;
import com.hls.reports.service.UserDetailsService;
import com.hls.reports.serviceImpl.JWTServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DocumentController {

	@Autowired
	private PDFConvertorService pdfConvertorService;
	@Autowired
	private SpringTemplateEngine springTemplateEngine;
	@Autowired
	private DataMapperService dataMapperService;
	@Autowired
	private JWTServiceImpl jwtService;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private  ReportDetailRepository reportDetailRepository;
	@Autowired
	private  ReportTemplateRepository reportTemplateRepository;

	@GetMapping(value = "/generate-document")
	public String generateDocument(@RequestParam("templateId") Integer templateId,@RequestHeader("Authorization") String authHeader)
			throws ParserConfigurationException, IOException {
		String token = authHeader.substring(7);
		String email = jwtService.getEmail(token);
		String finalHtml = null;
		User user = userRepository.findByEmailIdIgnoreCase(email)
		.orElseThrow(() -> new ReportException("User Not Found"));
		
		ReportTemplate template = reportTemplateRepository.findById(templateId)
		.orElseThrow(() -> new ReportException("Template Not Found"));
		
		Optional<ReportDetail> reportEnitity = reportDetailRepository.findByUserAndReportTemplate(user,template);
		reportEnitity.get().getReport();
		ObjectMapper mapper = new ObjectMapper();
        ReportsDto dto = mapper.readValue(reportEnitity.get().getReport(), ReportsDto.class);
		Context dataContext = dataMapperService.setData(dto);
		finalHtml = springTemplateEngine.process("reportTemplate1", dataContext);
		pdfConvertorService.htmlToPdfConvertor(finalHtml);
		return "Success";
	}

	@PostMapping(value = "/save-report")
	public ResponseEntity<String> saveJson(@RequestParam("templateId") Integer templateId, @RequestBody ReportsDto userList,
			@RequestHeader("Authorization") String authHeader) throws ParserConfigurationException, IOException {
		String token = authHeader.substring(7);
		String email = jwtService.getEmail(token);
		userDetailsService.saveJsonInDb(userList, templateId, email);
		return ResponseEntity.ok("Saved Report successfully");
	}
}
