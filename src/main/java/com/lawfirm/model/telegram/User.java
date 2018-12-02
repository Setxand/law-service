package com.lawfirm.model.telegram;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
public class User {

	@Id
	private Integer chatId;
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	public User() {
	}

	public User(Integer chatId) {
		this.chatId = chatId;
	}
}
