package com.lawfirm.validator;

import com.lawfirm.dto.telegram.Message;
import com.lawfirm.exception.BotException;
import com.lawfirm.service.telegram.MetaService;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MessageValidator {
	private final MetaService metaService;

	private enum ImageType {
		JPG,
		PNG
	}

	public MessageValidator(MetaService metaService) {
		this.metaService = metaService;
	}


	public void validateMessage(Message message) {
		String text = message.getText();

		if (urlHeaderCheck(text) && urlImageCheck(text)) {
			metaService.helpAskBackgroundImage(message);
			return;
		}

		throw new BotException(message.getChat().getId(), "I don`t know this command");
	}

	private boolean urlImageCheck(String message) {
		return Arrays.stream(ImageType.values()).anyMatch(t -> message.endsWith("." + t.name().toLowerCase()));
	}

	private boolean urlHeaderCheck(String message) {
		return message.startsWith("https://") || message.startsWith("http://");
	}

}
