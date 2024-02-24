package com.hls.reports.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class Patient {

	private String	name;
	private String	age_sex;
	private String	patient_id;
	private String	dob;
	private String	medications;
	private String	ordering_dr;
}
