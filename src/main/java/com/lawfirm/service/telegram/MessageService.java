package com.lawfirm.service.telegram;

import com.lawfirm.dto.telegram.Message;
import com.lawfirm.dto.telegram.TelegramEntity;
import com.lawfirm.model.lawProject.EditableComponent;
import com.lawfirm.model.telegram.User;
import com.lawfirm.reposiory.EditableComponentRepository;
import com.lawfirm.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

	private final MessageSenderService senderService;
	private final BotCommandService botCommandService;
	private final UserService userService;
	private final EditableComponentRepository editableComponentRepo;
	private final BotCommandServiceHelper commandHelper;

	public MessageService(MessageSenderService senderService, BotCommandService botCommandService, UserService userService, EditableComponentRepository editableComponentRepo, BotCommandServiceHelper commandHelper) {
		this.senderService = senderService;
		this.botCommandService = botCommandService;
		this.userService = userService;
		this.editableComponentRepo = editableComponentRepo;
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

	public void settingTitle(Message message, User user) {
		EditableComponent editableComponent = editableComponentRepo.findByComponentKey("TITLE")
				.orElseGet(() -> new EditableComponent("TITLE", "Hi!"));

		editableComponent.setValue(message.getText());
		editableComponentRepo.saveAndFlush(editableComponent);
		user.setStatus(null);
		userService.save(user);
		senderService.simpleMessage("A new title has been set - " + editableComponent.getValue(), message);
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
				settingTitle(message, user);
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
		}
	}

	private void checkingByStatus(Message message) {
	}
}


