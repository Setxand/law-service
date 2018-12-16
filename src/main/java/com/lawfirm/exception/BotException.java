package com.lawfirm.exception;

import lombok.Getter;

@Getter
public class BotException extends RuntimeException {
	private Integer chatId;

	public BotException(Integer chatId, String message) {
		super(message);
		this.chatId = chatId;
	}

	//Internal server
	public BotException(Integer chatId) {
		super("Internal server error");
		this.chatId = chatId;
	}


}
