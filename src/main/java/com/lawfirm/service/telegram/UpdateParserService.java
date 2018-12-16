package com.lawfirm.service.telegram;

import com.lawfirm.dto.telegram.Update;
import org.springframework.stereotype.Service;

@Service
public class UpdateParserService {

	private final MessageService messageService;
	private final CallBackService callBackService;
	public UpdateParserService(MessageService messageService, CallBackService callBackService) {
		this.messageService = messageService;
		this.callBackService = callBackService;
	}

	public void parseUpdate(Update update) {
		if (update.getCallBackQuery() != null) {
			callBackService.parseCallBack(update.getCallBackQuery());
		}
		if (update.getMessage() != null) {
			messageService.parseMessage(update.getMessage());
		}
	}
}
