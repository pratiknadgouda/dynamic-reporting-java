package com.hls.reports.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
	

	public UserDetailsService userDetailService();
}
