package com.lawfirm.service.telegram;

import com.lawfirm.dto.telegram.Message;
import com.lawfirm.dto.telegram.TelegramRequest;
import com.lawfirm.model.telegram.User;
import com.lawfirm.reposiory.EditableComponentRepository;
import com.lawfirm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ResourceBundle;

import static com.lawfirm.model.telegram.UserStatus.NEW_SERVICE_NAME;
import static com.lawfirm.model.telegram.UserStatus.SETTING_TITLE;

@Service
public class BotCommandService {
	private final EditableComponentRepository editableComponentRepo;
	private final MessageSenderService senderService;
	private final UserService userService;
	private final BotCommandServiceHelper helper;

	public BotCommandService(EditableComponentRepository editableComponentRepo, MessageSenderService senderService, UserService userService, BotCommandServiceHelper helper) {
		this.editableComponentRepo = editableComponentRepo;
		this.senderService = senderService;
		this.userService = userService;
		this.helper = helper;
	}


	public void parseBotCommand(Message message) {
		switch (message.getText()) {

			case "/start" :
				start(message);
				break;

			case "/changetitle" :
				changeTitle(message);
				break;
				
			case "/newservice" :
				newService(message);
		}
	}


	@Transactional
	public void newService(Message message) {
		User user = userService.getUser(message.getChat().getId());
		user.setStatus(NEW_SERVICE_NAME);
		userService.save(user);
		senderService.simpleMessage("Enter name of the new service : ", message);
	}

	@Transactional
	public void start(Message message) {
		User user = userService.getOrNew(message.getChat().getId());
		user.setChatId(message.getChat().getId());

		userService.save(user);

		String helloMessage = ResourceBundle.getBundle("dictionary").getString("HELLO_MESSAGE");
		senderService.simpleMessage(helloMessage, message);
	}

	@Transactional
	public void changeTitle(Message message) {
		senderService.sendMessage(new TelegramRequest("Enter a new title : ", message.getChat().getId()));
		User user = userService.getUser(message.getChat().getId());
		user.setStatus(SETTING_TITLE);
		userService.save(user);
	}


}
