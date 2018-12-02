package com.lawfirm.model.telegram;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

	@Id
	private Integer chatId;
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	public User(Integer chatId) {
		this.chatId = chatId;
	}
}
