package com.lawfirm.service.telegram;

import com.lawfirm.dto.telegram.Message;
import com.lawfirm.exception.BotException;
import com.lawfirm.model.telegram.User;
import com.lawfirm.service.UserService;
import com.lawfirm.validator.MessageValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {

	private final BotCommandService botCommandService;
	private final UserService userService;
	private final BotCommandServiceHelper commandHelper;
	private final MessageValidator validator;

	public MessageService(BotCommandService botCommandService,
						  UserService userService, BotCommandServiceHelper commandHelper, MessageValidator validator) {
		this.botCommandService = botCommandService;
		this.userService = userService;
		this.commandHelper = commandHelper;
		this.validator = validator;
	}

	@Transactional
	public void parseMessage(Message message) {

		if (message.getText() != null) {
			if (message.getText().startsWith("/")) {

				botCommandService.parseBotCommand(message);
			} else {
				notStart(message);
			}
		} else {
			throw new BotException(message.getChat().getId());
		}
	}


	private void notStart(Message message) {
		User user = userService.getUser(message.getChat().getId());

		if (user.getStatus() != null)
			userStatus(message, user);
		else
			validator.validateMessage(message);
	}

	private void userStatus(Message message, User user) {
		switch (user.getStatus()) {
			case SETTING_TITLE:
				commandHelper.settingTitle(message, user);
				break;
			case NEW_SERVICE_NAME:
				commandHelper.helpAddNewService(message);
				break;
			case NEW_SERVICE_DESCRIPTION:
				commandHelper.helpAddNewService(message);
				break;
			case NEW_SERVICE_IMAGE:
				commandHelper.helpAddNewService(message);
				break;
			case ARTICLE_CONTENT:
				commandHelper.helpSetArticle(message, user);
				break;
			case BACKGROUND_1:
				commandHelper.helpSetBackground(message, user);
				break;
			default:
				throw new BotException(message.getChat().getId());
		}
	}


}


