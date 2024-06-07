package com.board.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "persistent_logins")
@Entity
@Getter @Setter
public class PersistentLogins {

	@Id
	@Column(length = 64)
	private String series;
	
	@Column(nullable = false, length = 64)
	private String username;
	
	@Column(nullable = false, length = 64)
	private String token;
	
	@Column(name = "last_used", nullable = false, length = 64)
	private LocalDateTime lastUsed;
}
