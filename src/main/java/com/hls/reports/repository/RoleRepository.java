package com.hls.reports.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hls.reports.entity.Role;



public interface RoleRepository extends JpaRepository<Role, Integer> {
	public Optional<String> findNameById(Integer id);
	public Optional<Role>  findById(Integer id);
}
