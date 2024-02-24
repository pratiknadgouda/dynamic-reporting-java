package com.hls.reports.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import com.hls.reports.serviceImpl.UserServiceImpl;
import com.hls.reports.validation.RequestValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	private UserRepository userRepository;
	@Autowired
	private ReportDetailRepository reportDetailRepository;
	@Autowired
	private ReportTemplateRepository reportTemplateRepository;

	@PostMapping(value = "/generate-document")
	public ResponseEntity<?> generateDocument(@RequestParam("templateId") Integer templateId,
			@RequestHeader("Authorization") String authHeader, @RequestBody(required = false) ReportsDto userList,
			@RequestParam(required = false) Integer id) throws ParserConfigurationException, IOException {
		String token = authHeader.substring(7);
		Context dataContext = null;
		String email = jwtService.getEmail(token);
		String finalHtml = null;
		User user = null;
		if (Objects.nonNull(id)) {
			user = userRepository.findById(id).orElseThrow(() -> new ReportException("User Not Found"));
		} else {

			user = userRepository.findById(id).orElseThrow(() -> new ReportException("User Not Found"));
		}

		ReportTemplate template = reportTemplateRepository.findById(templateId)
				.orElseThrow(() -> new ReportException("Template Not Found"));
		ReportDetail reportEnitity = reportDetailRepository.findByUserAndReportTemplate(user, template)
				.orElseThrow(() -> new ReportException("Template Not Found"));
		
		ObjectMapper mapper = new ObjectMapper();
		ReportsDto dto = mapper.readValue(reportEnitity.getReport(), ReportsDto.class);
		if (Objects.nonNull(userList)) {
			dataContext = dataMapperService.setData(userList);
		} else {

			dataContext = dataMapperService.setData(dto);
		}
		finalHtml = springTemplateEngine.process("reportTemplate1", dataContext);
		String path = pdfConvertorService.htmlToPdfConvertor(finalHtml, template.getTemplateName());
		File file = new File(path);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		String headerValue = "attachment; filename=" + file.getName();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		headers.add("Content-Disposition", headerValue);

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

	@PostMapping(value = "/save-report")
	public ResponseEntity<String> saveJson(@RequestParam("templateId") Integer templateId,
			@RequestBody ReportsDto userList, @RequestHeader("Authorization") String authHeader)
			throws ParserConfigurationException, IOException {
		String token = authHeader.substring(7);
		String email = jwtService.getEmail(token);
		userDetailsService.saveJsonInDb(userList, templateId, email);
		return ResponseEntity.ok("Saved Report successfully");
	}
}
