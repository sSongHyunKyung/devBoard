package com.board.account;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SignUpForm {
	
	@NotBlank
	@Length(min = 3, max = 20)
	private String nickname;

	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	@Length(min = 8, max = 50)
	private String password;
	
}
