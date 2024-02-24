package com.hls.reports.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class ReportDto {

	private String		report_date_time;
	private Patient		patient_info;
	private List<TestType>	test_types;
}
