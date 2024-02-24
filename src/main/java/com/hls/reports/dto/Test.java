package com.hls.reports.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class Test {

	private String	test_name;
	private String	observed_value;
	private String	unit;
	private String	reference_range;
}
