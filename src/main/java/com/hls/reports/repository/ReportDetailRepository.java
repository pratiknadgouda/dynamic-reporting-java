package com.hls.reports.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hls.reports.entity.ReportDetail;
import com.hls.reports.entity.Role;
import com.hls.reports.entity.User;

public interface ReportDetailRepository extends JpaRepository<ReportDetail, Integer> {
	ReportDetail findByUser(Optional<User> user);
}
