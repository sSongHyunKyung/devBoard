package com.board.account;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.Account;
import com.board.settings.Profile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService implements UserDetailsService{

	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	
	
	public Account saveAccount(@Valid SignUpForm signUpForm) {
		Account account = Account.builder()
				.email(signUpForm.getEmail())
				.nickname(signUpForm.getNickname())
				.password(passwordEncoder.encode(signUpForm.getPassword()))
				.build();
		
		return accountRepository.save(account);
	}

	public void login(Account account) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				account.getNickname(), 
				account.getPassword(), 
				List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
		
	}

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String emailOrNickname) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(emailOrNickname);
		if(account == null) {
			account = accountRepository.findByNickname(emailOrNickname);
		}
		
		if(account == null) {
			throw new UsernameNotFoundException(emailOrNickname);
		}
		
		
		return new UserAccount(account);
	}

	public void completeSignUp(Account account) {
		login(account);
		
	}
	
	//　プロフィール修正機能
	public void updateProfile(Account account, @Valid Profile profile) {
		modelMapper.map(profile, account);
		accountRepository.save(account);
		
	}

	// パスワード修正機能
	public void updatePassword(Account account, String newPassword) {
		account.setPassword(passwordEncoder.encode(newPassword));
		accountRepository.save(account);
	}

	// nickname修正機能
	public void updateNickname(Account account, String nickname) {
		account.setNickname(nickname);
		accountRepository.save(account);
		login(account);	
	}
}  
