package com.lawfirm.service.telegram;

import com.lawfirm.dto.telegram.Message;
import com.lawfirm.exception.BotException;
import com.lawfirm.model.lawProject.EditableComponent;
import com.lawfirm.model.lawProject.ServiceBody;
import com.lawfirm.model.lawProject.ServiceTitle;
import com.lawfirm.model.telegram.User;
import com.lawfirm.reposiory.EditableComponentRepository;
import com.lawfirm.reposiory.EditableComponents;
import com.lawfirm.reposiory.ServiceBodyRepo;
import com.lawfirm.reposiory.ServiceTitleRepo;
import com.lawfirm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ResourceBundle;

import static com.lawfirm.model.telegram.User.UserStatus.*;
import static com.lawfirm.service.telegram.CallBackData.BACKGROUND_QUESTION;

@Service
public class BotCommandServiceHelper {

	private final UserService userService;
	private final MessageSenderService senderService;
	private final ServiceTitleRepo serviceTitleRepo;
	private final ServiceBodyRepo serviceBodyRepo;
	private final EditableComponentRepository editableComponentRepo;

	public BotCommandServiceHelper(UserService userService, MessageSenderService senderService, ServiceTitleRepo serviceTitleRepo, ServiceBodyRepo serviceBodyRepo, EditableComponentRepository editableComponentRepo) {
		this.userService = userService;
		this.senderService = senderService;
		this.serviceTitleRepo = serviceTitleRepo;
		this.serviceBodyRepo = serviceBodyRepo;
		this.editableComponentRepo = editableComponentRepo;
	}

	@Transactional
	public void helpAddNewService(Message message) {
		User user = userService.getUser(message.getChat().getId());

		switch (user.getStatus()) {
			case NEW_SERVICE_NAME:
				newServiceName(message, user);
				break;
			case NEW_SERVICE_DESCRIPTION:
				newServiceDescription(message, user);
				break;
			case NEW_SERVICE_IMAGE:
				newServiceImage(message, user);
				break;
		}
	}

	@Transactional
	public void settingTitle(Message message, User user) {
		EditableComponent editableComponent = editableComponentRepo.findByComponentKey(EditableComponents.TITLE)
				.orElseGet(() -> new EditableComponent(EditableComponents.TITLE, "Hi!"));

		editableComponent.setValue(message.getText());
		editableComponentRepo.saveAndFlush(editableComponent);
		user.setStatus(null);
		userService.save(user);
		senderService.simpleMessage("A new title has been set - " + editableComponent.getValue(), message);
	}

	@Transactional
	public void newServiceImage(Message message, User user) {
		ServiceTitle serviceTitle = serviceTitleRepo.findTopByOrderByIdDesc();
		serviceTitle.setImage(message.getText());
		serviceTitleRepo.saveAndFlush(serviceTitle);

		user.setStatus(ARTICLE_CONTENT);
		userService.save(user);
		senderService.simpleMessage("Enter the content of the article (10000 symbols) : ", message);

		ServiceBody body = new ServiceBody();
		body.setTitle(serviceTitle.getTitle());
		body.setId(serviceTitle.getId());
		serviceBodyRepo.saveAndFlush(body);
	}

	@Transactional
	public void helpSetArticle(Message message, User user) {

		if (message.getText().length() > 10000) {
			throw new BotException(message.getChat().getId(), "Too large content, need no more, than 10000 symbols");
		}
		ServiceBody body = serviceBodyRepo.findTopByOrderByIdDesc()
				.orElseThrow(() -> new BotException(message.getChat().getId(), "failed to load content"));

		body.setBody(message.getText());
		serviceBodyRepo.saveAndFlush(body);
		user.setStatus(null);
		userService.save(user);

		senderService.simpleMessage("Service successfully added", message);
	}

	@Transactional
	public void helpSetBackground(Message message, User user) {
		EditableComponent background = editableComponentRepo
				.findByComponentKey(EditableComponents.BACKGROUND_IMAGE)
				.orElseThrow(() -> new BotException(message.getChat().getId(), "Invalid background image ID"));

		background.setValue(message.getText());
		senderService.simpleMessage("background successfully added", message);
		user.setStatus(null);
	}

	@Transactional
	public void helpBackgroundImage(Message message) {
		User user = userService.getUser(message.getChat().getId());
		user.setMetaData(message.getText());
		user.setStatus(User.UserStatus.BACKGROUND_QUESTION);

		senderService.simpleQuestion(BACKGROUND_QUESTION, "?", getBundle(BACKGROUND_QUESTION.name()), message);
	}

	private void newServiceDescription(Message message, User user) {
		ServiceTitle serviceTitle = serviceTitleRepo.findTopByOrderByIdDesc();
		serviceTitle.setContent(message.getText());
		user.setStatus(NEW_SERVICE_IMAGE);
		senderService.simpleMessage("Add image : ", message);

		serviceTitleRepo.saveAndFlush(serviceTitle);
		userService.save(user);
	}

	private void newServiceName(Message message, User user) {
		ServiceTitle serviceTitle = new ServiceTitle();
		serviceTitle.setTitle(message.getText());
		serviceTitleRepo.save(serviceTitle);
		senderService.simpleMessage("Enter description : ", message);
		user.setStatus(NEW_SERVICE_DESCRIPTION);
		userService.save(user);
	}

	private String getBundle(String var) {
		return ResourceBundle.getBundle("messages").getString(var);
	}
}
