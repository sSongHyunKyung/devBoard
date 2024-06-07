package com.board.settings;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.account.AccountService;
import com.board.account.CurruntUser;
import com.board.domain.Account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SettingsController {
	

	
	
	private final AccountService accountService;
	private final ModelMapper modelMapper;
	private final NicknameValidator nicknameValidator;
	
	@InitBinder("passwordForm")
	public void passwordFormInitBinder(WebDataBinder webDataBinder) {
	    webDataBinder.addValidators(new PasswordFormValidator());
	}

	@InitBinder("nicknameForm")
	public void nicknameFormInitBinder(WebDataBinder webDataBinder) {
	    webDataBinder.addValidators(nicknameValidator);
	}
	
	static final String SETTINGS_PROFILE_URL = "settings/profile";
	static final String SETTINGS_PASSWORD_URL = "settings/password";
	static final String SETTINGS_ACCOUNT_URL = "settings/account";
	
	
	@GetMapping("/"+SETTINGS_PROFILE_URL)
	public String updateProfileForm(@CurruntUser Account account, Model model) {
		model.addAttribute(account);
		//model.addAttribute(modelMapper.map(account, Profile.class));
		model.addAttribute(new Profile(account));
		return SETTINGS_PROFILE_URL;
	}
	
	@PostMapping("/"+SETTINGS_PROFILE_URL)
	public String updateProfile(@CurruntUser Account account, @Valid Profile profile, Errors erros, Model model, RedirectAttributes attributes) {
		if (erros.hasErrors()) {
			model.addAttribute(account);
			return SETTINGS_PROFILE_URL; 
		}
		
		//修正
		accountService.updateProfile(account,profile);
		attributes.addFlashAttribute("message","修正を完了しました。");
		return "redirect:/"+SETTINGS_PROFILE_URL;
	}
	
	
	@GetMapping("/"+SETTINGS_PASSWORD_URL)
	public String updatePasswordForm(@CurruntUser Account account, Model model) {
		model.addAttribute(account);
		model.addAttribute(new PasswordForm());
		return SETTINGS_PASSWORD_URL;
	}
	
	@PostMapping("/"+SETTINGS_PASSWORD_URL)
	public String updatePassword(@CurruntUser Account account, @Valid PasswordForm passwordForm, Errors erros,
			Model model, RedirectAttributes attributes) {
		if (erros.hasErrors()) {
			model.addAttribute(account);
			return SETTINGS_PASSWORD_URL;
		}
		accountService.updatePassword(account, passwordForm.getNewPassword());
		attributes.addFlashAttribute("message", "パスワードを変更しました。");
		return "redirect:/"+SETTINGS_PASSWORD_URL;
	}
	
	
	@GetMapping("/"+SETTINGS_ACCOUNT_URL)
	public String updateAccountForm(@CurruntUser Account account, Model model) {
		model.addAttribute(account);
		model.addAttribute(new NicknameForm());
		return SETTINGS_ACCOUNT_URL;
	}
	
	@PostMapping("/"+SETTINGS_ACCOUNT_URL)
	public String updateAccount(@CurruntUser Account account, @Valid NicknameForm nicknameForm, Errors erros,
			Model model, RedirectAttributes attributes) {
		if (erros.hasErrors()) {
			model.addAttribute(account);
			return SETTINGS_ACCOUNT_URL;
		}
		accountService.updateNickname(account, nicknameForm.getNickname());
		attributes.addFlashAttribute("message", "Nicknameを変更しました。");
		return "redirect:/"+SETTINGS_ACCOUNT_URL;
	}
	
	
}
