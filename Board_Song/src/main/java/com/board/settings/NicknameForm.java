package com.board.settings;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NicknameForm {

	@NotBlank
	@Length(min = 3, max = 20)
	private String nickname;
}
