package com.board.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.board.account.AccountService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final AccountService accountService;
	private final DataSource dataSource;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new  MvcRequestMatcher.Builder(introspector);
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers(
                        mvcMatcherBuilder.pattern("/"),
                        mvcMatcherBuilder.pattern("/login"),
                        mvcMatcherBuilder.pattern("/sign-up"),             
                        mvcMatcherBuilder.pattern("/email-login"),
                        mvcMatcherBuilder.pattern("/login-link"),
                        mvcMatcherBuilder.pattern("/profile/*"),
                        mvcMatcherBuilder.pattern(HttpMethod.GET,"/profile/*")
                ).permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .anyRequest().authenticated()              
        );
        
        http.formLogin()
        .loginPage("/login").permitAll();
        
        http.logout().logoutSuccessUrl("/");
        
        http.rememberMe()
        	.tokenRepository(tokenRepository())
        	.userDetailsService(accountService);

        return http.build();
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
    	JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
    	jdbcTokenRepository.setDataSource(dataSource);
    	return jdbcTokenRepository;
    }

}
