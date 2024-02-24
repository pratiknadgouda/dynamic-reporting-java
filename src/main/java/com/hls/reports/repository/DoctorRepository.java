package com.hls.reports.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hls.reports.entity.Doctor;
import com.hls.reports.entity.User;


public interface DoctorRepository extends JpaRepository<Doctor, Integer>{
	
	Doctor findByUser(Optional<User> user);

}
