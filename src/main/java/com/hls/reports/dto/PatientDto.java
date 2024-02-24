package com.hls.reports.dto;

import java.util.List;

import com.hls.reports.entity.Doctor;
import com.hls.reports.entity.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PatientDto {
	private Patient patient;
	private Doctor doctor;

}
