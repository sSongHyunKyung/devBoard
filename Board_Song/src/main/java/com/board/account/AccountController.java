package com.board.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.board.domain.Account;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccountController {
	
	private final SignUpFormValidator signUpFormValidator;
	private final AccountService accountService;
	private final AccountRepository accountRepository;
	
	
	@InitBinder("signUpForm")
	public void initBinder(WebDataBinder webDataBinder) {
	    webDataBinder.addValidators(signUpFormValidator);
	}
	
	
	@GetMapping("/sign-up")
	public String signUpForm(Model model) {
		model.addAttribute("signUpForm", new SignUpForm());
		return "account/sign-up";
	}
	
	@PostMapping("/sign-up")
	public String signUpSubmit(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors) {
		
		if(errors.hasErrors()) {
			return "account/sign-up"; 
		}

		// 会員登録処理
		Account account = accountService.saveAccount(signUpForm);
		// ログイン
		accountService.completeSignUp(account);
		
		return "index";
	}
	
	@GetMapping("/profile/{nickname}")
	public String viewProfile(@PathVariable("nickname") String nickname, Model model, @CurruntUser Account account) {
		Account byNickname = accountRepository.findByNickname(nickname);
		
		if (nickname == null) {
			throw new IllegalArgumentException(nickname + "の方は存じません。");
		}
		
		
		model.addAttribute(byNickname);
		model.addAttribute("isOwner", byNickname.equals(account));
		
	    
		return "account/profile";
		
		
	}



	
}
