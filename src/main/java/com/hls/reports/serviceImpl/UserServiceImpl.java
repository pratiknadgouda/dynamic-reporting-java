package com.hls.reports.serviceImpl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hls.reports.dto.LoginDto;
import com.hls.reports.entity.Role;
import com.hls.reports.entity.User;
import com.hls.reports.exceptions.ReportException;
import com.hls.reports.repository.RoleRepository;
import com.hls.reports.repository.UserRepository;
import com.hls.reports.response.JwtResponse;
import com.hls.reports.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final JWTServiceImpl jwtService;
	private final RoleRepository roleRepository; 
	
	
	@Override
	public UserDetailsService userDetailService() {
		
		return new  UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				System.out.println("username:-"+ username);
				return userRepository.findByEmailIdIgnoreCase(username)
						.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			}
			
		};
		
	}

	public JwtResponse login(@Valid LoginDto loginDto) {
		
		boolean isValid = validateUser(loginDto);
		if (!isValid) {
			
			throw new ReportException("Invalid user logged in");
		}
		String userName = getUserName(loginDto.getEmail());
		return jwtService.generateToken(loginDto.getEmail(), userName, findRoles(loginDto.getEmail()));
	}

	private String findRoles(String email) {
		User user = findbyEmailId(email);
		Optional<Role> role =  roleRepository.findById(user.getRole_id());
		return role.orElseThrow(() -> new ReportException("Username not found")).getName();

	}

	private String getUserName(String email) {
		Optional<User> userOptional = userRepository.findByEmailIdIgnoreCase(email);
		return userOptional.orElseThrow(() -> new ReportException("Username not found")).getName();
	}

	private boolean validateUser(@Valid LoginDto loginDto) {
		return findbyEmailId(loginDto.getEmail()) != null;
	}

	
	public User findbyEmailId(String email) {
		
		return userRepository.findByEmailIdIgnoreCase(email).orElseThrow(
				() -> new ReportException("User Not Found"));
	}

}
