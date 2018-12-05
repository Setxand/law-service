package com.lawfirm.service.telegram;

import com.lawfirm.dto.telegram.Message;
import com.lawfirm.dto.telegram.TelegramEntity;
import com.lawfirm.exception.BotException;
import com.lawfirm.model.telegram.User;
import com.lawfirm.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

	private final MessageSenderService senderService;
	private final BotCommandService botCommandService;
	private final UserService userService;
	private final BotCommandServiceHelper commandHelper;

	public MessageService(MessageSenderService senderService, BotCommandService botCommandService,
						  UserService userService, BotCommandServiceHelper commandHelper) {
		this.senderService = senderService;
		this.botCommandService = botCommandService;
		this.userService = userService;
		this.commandHelper = commandHelper;
	}

	public void parseMessage(Message message) {

		if (message.getText() != null) {
			if (message.getText().equals("/start")) {

				botCommandService.parseBotCommand(message);
			} else {
				notStart(message);
			}
		} else {
			notStart(message);
		}
	}


	private void notStart(Message message) {
		User user = userService.getUser(message.getChat().getId());
		if (user.getStatus() != null)
			userStatus(message, user);

		else if (message.getEntities() != null) {
			List<TelegramEntity> entities = message.getEntities();
			for (TelegramEntity telegramEntity : entities) {
				if (telegramEntity.getType().equals("url")) {
					checkingByStatus(message);
					break;
				}
				botCommandService.parseBotCommand(message);
			}
			return;
		}
		checkingByStatus(message);
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
				throw new BotException(message.getChat().getId(), "Internal server error");
		}
	}

	private void checkingByStatus(Message message) {
	}
}


