package com.hls.reports.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
public class LoginDto  {

	private static final long	serialVersionUID	= -4522411376681996928L;
	@NotBlank
	@Email(message = "Invalid Email", regexp = "^[A-Za-z0-9+_.-]+@persistent(.+)com$")
	String						email;

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}
}