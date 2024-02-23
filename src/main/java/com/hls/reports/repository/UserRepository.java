package com.hls.reports.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hls.reports.entity.User;



public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("SELECT u FROM User u WHERE upper(u.emailId)=upper(:email)")
	public Optional<User> findByEmailIdIgnoreCase(@Param("email") String email);
	
	public User findByNameAndEmailIdIgnoreCase(String name, String email);

}
