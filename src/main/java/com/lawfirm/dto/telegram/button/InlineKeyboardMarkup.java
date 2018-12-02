package com.lawfirm.dto.telegram.button;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class InlineKeyboardMarkup implements Markup {
	@JsonProperty("inline_keyboard")
	private List<List<InlineKeyboardButton>> inlineKeyBoard;

	public InlineKeyboardMarkup(List<List<InlineKeyboardButton>> inlineKeyBoard) {
		this.inlineKeyBoard = inlineKeyBoard;
	}


}
