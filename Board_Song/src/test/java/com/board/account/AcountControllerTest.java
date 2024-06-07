package com.board.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.Account;

@SpringBootTest
@AutoConfigureMockMvc
public class AcountControllerTest {

	
	@Autowired private MockMvc mockMvc;
	
	@Autowired private AccountRepository accountRepository;
	
	
	@DisplayName("会員登録処理　ー非常")
	@Test
	void signUpSubmit_with_wrong_input() throws Exception {
		mockMvc.perform(post("/sign-up")
				.param("nickname", "yamada")
				.param("email", "email..")
				.param("password", "12345")
				.with(csrf()))
					.andExpect(status().isOk())
					.andExpect(view().name("account/sign-up"))
					.andExpect(unauthenticated());

	}
	
	@DisplayName("会員登録処理　ー常")
	@Test
	void signUpSubmit_with_correct_input() throws Exception {
		mockMvc.perform(post("/sign-up")
				.param("nickname", "yamada2")
				.param("email", "email1234@gmail.com")
				.param("password", "12345678")
				.param("repassword", "12345678")
				.with(csrf()))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/"))
					.andExpect(authenticated());
			
		accountRepository.existsByNickname("yamada2");
		Account account = accountRepository.findByEmail("email1234@gmail.com"); 
		assertNotNull(account);
		assertNotEquals(account.getPassword(),"12345678");
	
	}
}
