package com.board.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.board.account.CurruntUser;
import com.board.domain.Account;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String home(@CurruntUser Account account,Model model) {
		if(account != null) {
			model.addAttribute(account);
		}
		
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "account/login";
	}
	
}
