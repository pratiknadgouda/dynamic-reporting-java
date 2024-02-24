package com.hls.reports.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hls.reports.dto.DoctorDto;
import com.hls.reports.dto.PatientDto;
import com.hls.reports.dto.ReportDto;
import com.hls.reports.dto.ReportsDto;
import com.hls.reports.entity.Doctor;
import com.hls.reports.entity.Patient;
import com.hls.reports.entity.ReportDetail;
import com.hls.reports.entity.ReportTemplate;
import com.hls.reports.entity.User;
import com.hls.reports.exceptions.ReportException;
import com.hls.reports.repository.DoctorRepository;
import com.hls.reports.repository.PatientRepository;
import com.hls.reports.repository.ReportDetailRepository;
import com.hls.reports.repository.ReportTemplateRepository;
import com.hls.reports.repository.UserRepository;
import com.hls.reports.service.UserDetailsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetailsService {

	private final UserRepository userRepository;
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;
	private final ReportDetailRepository reportDetailRepository;
	private final ReportTemplateRepository reportTemplateRepository;
	private final ObjectMapper objectMapper;

	@Override
	public DoctorDto getDoctorDetails(String email) {
		Optional<User> user = userRepository.findByEmailIdIgnoreCase(email);
		if (user.isPresent()) {
			Doctor doctor = doctorRepository.findByUser(user);
			if (doctor != null) {
				DoctorDto doctorDto = new DoctorDto();
				doctorDto.setDoctor(doctor);
				doctorDto.setPatients(doctor.getPatients());
				return doctorDto;
			}
		}
		return null;
	}

	@Override
	public PatientDto getPatientDetails(String email) {
		Optional<User> user = userRepository.findByEmailIdIgnoreCase(email);
		if (user.isPresent()) {
			Patient patient = patientRepository.findByUser(user);
			if (patient != null) {
				PatientDto patientDto = new PatientDto(patient, patient.getDoctor());
				return patientDto;
			}
		}
		return null;

	}

	@Override
	public ReportDto getUserReport(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {

			ReportDetail reportDetail = reportDetailRepository.findByUser(user);
			ReportDto reportDto = new ReportDto();
			reportDto.setJsonReport(reportDetail.getReport());
			return reportDto;
		}
		return null;
	}

	@Override
	public List<ReportTemplate> getAllTemplates() {
		List<ReportTemplate> templates = reportTemplateRepository.findAll();
		return templates;
	}

	@Override
	public void saveJsonInDb(ReportsDto report, Integer templateId, String email) {
		ObjectMapper Obj = new ObjectMapper();
		try {
			String jsonReport = Obj.writeValueAsString(report);
			ReportTemplate templateEntity = reportTemplateRepository.findById(templateId)
					.orElseThrow(() -> new ReportException("Template Not Found"));
			User userEntity = userRepository.findByEmailIdIgnoreCase(email)
					.orElseThrow(() -> new ReportException("User Not Found"));
			ReportDetail reportEntity = new ReportDetail();
			reportEntity.setReport(jsonReport);
			reportEntity.setUser(userEntity);
			reportEntity.setReportTemplate(templateEntity);
			reportDetailRepository.save(reportEntity);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
