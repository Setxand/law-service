package com.lawfirm.model.telegram;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Data
public class User {

	public enum UserStatus {

		SETTING_TITLE,
		NEW_SERVICE_NAME,
		NEW_SERVICE_DESCRIPTION,
		NEW_SERVICE_IMAGE,
		ARTICLE_CONTENT,
		BACKGROUND_1
	}

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
