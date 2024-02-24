package com.hls.reports.service;

import java.util.List;

import com.hls.reports.dto.DoctorDto;
import com.hls.reports.dto.PatientDto;
import com.hls.reports.dto.ReportDto;
import com.hls.reports.dto.ReportsDto;
import com.hls.reports.entity.ReportTemplate;

public interface UserDetailsService {
	DoctorDto getDoctorDetails(String email);
	PatientDto getPatientDetails(String email);
	ReportDto getUserReport(Integer id);
	List<ReportTemplate> getAllTemplates();
	void saveJsonInDb(ReportsDto report,Integer templateId,String email);
}
