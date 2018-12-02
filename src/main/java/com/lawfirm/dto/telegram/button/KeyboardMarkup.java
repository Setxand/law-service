package com.lawfirm.dto.telegram.button;

import lombok.Data;

import java.util.List;

@Data
public class KeyboardMarkup implements Markup {
	private List<List<KeyboardButton>> keyboard;

	public KeyboardMarkup(List<List<KeyboardButton>> keyboard) {
		this.keyboard = keyboard;
	}
}

