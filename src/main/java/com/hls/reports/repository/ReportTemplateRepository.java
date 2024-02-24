package com.hls.reports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hls.reports.entity.ReportTemplate;
import com.hls.reports.entity.Role;

public interface ReportTemplateRepository extends JpaRepository<ReportTemplate, Integer>{
	List<ReportTemplate> findAll();
}
