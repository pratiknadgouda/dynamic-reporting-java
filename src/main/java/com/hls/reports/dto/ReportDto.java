package com.hls.reports.dto;



import com.hls.reports.entity.Doctor;
import com.hls.reports.entity.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ReportDto {
	private String jsonReport;
	

}
