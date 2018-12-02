package com.lawfirm.service.telegram;

import com.lawfirm.dto.telegram.Update;
import org.springframework.stereotype.Service;

@Service
public class UpdateParserService {

	private final MessageService messageService;

	public UpdateParserService(MessageService messageService) {
		this.messageService = messageService;
	}

	public void parseUpdate(Update update) {
		if (update.getMessage() != null) {
			messageService.parseMessage(update.getMessage());
		}
	}
}
