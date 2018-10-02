package com.lawfirm.service.telegram;

import com.lawfirm.dto.telegram.Message;
import com.lawfirm.exception.BotException;
import com.lawfirm.model.lawProject.ServiceTitle;
import com.lawfirm.model.telegram.User;
import com.lawfirm.reposiory.ServiceTitleRepo;
import com.lawfirm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class BotCommandServiceHelper {

	private final UserService userService;
	private final MessageSenderService senderService;
	private final ServiceTitleRepo serviceTitleRepo;
	public BotCommandServiceHelper(UserService userService, MessageSenderService senderService, ServiceTitleRepo serviceTitleRepo) {
		this.userService = userService;
		this.senderService = senderService;
		this.serviceTitleRepo = serviceTitleRepo;
	}

	public void helpAddNewService(Message message) {
		User user = userService.getUser(message.getChat().getId());

		switch (user.getStatus()) {
			case "NEW_SERVICE_NAME" :
				newServiceName(message, user);
				break;
			case "NEW_SERVICE_DESCRIPTION" :
				newServiceDescription(message, user);
				break;
			case "NEW_SERVICE_IMAGE" :
				newServiceImage(message, user);
				break;
		}
	}

	@Transactional
	public void newServiceImage(Message message, User user) {
		ServiceTitle serviceTitle = serviceTitleRepo.findTopByOrderByIdDesc();
		serviceTitle.setImage(message.getText());
		serviceTitleRepo.saveAndFlush(serviceTitle);
		user.setStatus(null);
		userService.save(user);
		senderService.simpleMessage("New service title has been added", message);
	}

	private void newServiceDescription(Message message, User user) {
		ServiceTitle serviceTitle = serviceTitleRepo.findTopByOrderByIdDesc();
		serviceTitle.setContent(message.getText());
		user.setStatus("NEW_SERVICE_IMAGE");
		senderService.simpleMessage("Add image : ", message);

		serviceTitleRepo.saveAndFlush(serviceTitle);
		userService.save(user);
	}

	private void newServiceName(Message message, User user) {
		ServiceTitle serviceTitle = new ServiceTitle();
		serviceTitle.setTitle(message.getText());
		serviceTitleRepo.save(serviceTitle);
		senderService.simpleMessage("Enter description : ", message);
		user.setStatus("NEW_SERVICE_DESCRIPTION");
		userService.save(user);
	}

}
