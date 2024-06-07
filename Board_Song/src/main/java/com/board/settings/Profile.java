package com.board.settings;

import org.hibernate.validator.constraints.Length;

import com.board.domain.Account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Profile {

	@Length(max = 50)
	private String occupation;
	
	@Length(max = 50)
	private String location;
	
	
	@Length(max = 35)
	private String memo;

	
	public Profile(Account account) {
		this.occupation = account.getOccupation();
		this.location = account.getLocation();
		this.memo = account.getMemo();
	}
}
