package com.board.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Account {
	
	@Id @GeneratedValue
	private long id;
	
	@Column(unique = true)
	private String email;
	
	@Column(unique = true)
	private String nickname;
	
	private String password;
	
	//会員登録時間
	private LocalDateTime joinedAt;
	
	private String occupation;
	
	private String location;
	
	private String memo;
	
    @PrePersist
    protected void onCreate() {
        joinedAt = LocalDateTime.now();
    }
	
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Account account = (Account) o;
	    return id == account.id;
	}

    @Override
    public int hashCode() {
        return 31;
    }
}
