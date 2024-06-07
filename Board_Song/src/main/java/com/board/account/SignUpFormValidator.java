package com.board.account;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final AccountRepository accountRepository;
	
	// instance 検証
	@Override
	public boolean supports(Class<?> clazz) {
		
		return clazz.isAssignableFrom(SignUpForm.class);
	}
	
	
	// 有効性検査
	@Override
	public void validate(Object target, Errors errors) {
		SignUpForm signUpForm = (SignUpForm)target;
		
		if (accountRepository.existsByEmail(signUpForm.getEmail())) {
			errors.rejectValue("email", "invalid.email", new Object[] {signUpForm.getEmail()}, "存在しているメールです。");
		}
		
		if (accountRepository.existsByNickname(signUpForm.getNickname())) {
			errors.rejectValue("nickname", "invalid.nickname", new Object[] {signUpForm.getNickname()}, "存在しているnicknameです。");
		}
	
	}

}
