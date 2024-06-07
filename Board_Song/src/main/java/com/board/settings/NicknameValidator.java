package com.board.settings;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.board.account.AccountRepository;
import com.board.domain.Account;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NicknameValidator implements Validator {

	private final AccountRepository accountRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return NicknameForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		NicknameForm nicknameForm = (NicknameForm) target;
		Account byNickname = accountRepository.findByNickname(nicknameForm.getNickname());
		if (byNickname != null) {
			errors.rejectValue("nickname", "wrong.value", "使用できません。");
		}
		
	}

}
