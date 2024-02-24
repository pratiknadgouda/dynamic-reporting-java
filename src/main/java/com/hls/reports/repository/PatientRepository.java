package com.hls.reports.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hls.reports.entity.Patient;
import com.hls.reports.entity.User;

public interface PatientRepository  extends JpaRepository<Patient, Integer>{
	
	Patient findByUser(Optional<User> user);

}
