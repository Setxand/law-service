package com.lawfirm.service.telegram;

import com.lawfirm.dto.telegram.CallBackQuery;
import com.lawfirm.dto.telegram.Chat;
import com.lawfirm.dto.telegram.Message;
import com.lawfirm.exception.BotException;
import com.lawfirm.model.lawProject.EditableComponent;
import com.lawfirm.model.telegram.User;
import com.lawfirm.reposiory.EditableComponentRepository;
import com.lawfirm.reposiory.EditableComponents;
import com.lawfirm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MetaService {

	private final MessageSenderService senderService;
	private final UserService userService;
	private final EditableComponentRepository editableComponentRepo;

	public MetaService(MessageSenderService senderService, UserService userService, EditableComponentRepository editableComponentRepo) {
		this.senderService = senderService;
		this.userService = userService;
		this.editableComponentRepo = editableComponentRepo;
	}


	@Transactional
	public void backgroundQuestion(CallBackQuery callBackQuery) {
		User user = userService.getUser(callBackQuery.getFrom().getId());

		if (callBackQuery.getData().contains("QUESTION_YES")) {
			EditableComponent component = editableComponentRepo.findByComponentKey(EditableComponents.BACKGROUND_IMAGE)
					.orElseThrow(() -> new BotException(user.getChatId(), "Error"));
			component.setValue(user.getMetaData());
			senderService.simpleMessage("Background changed!", new Message(new Chat(user.getChatId())));
		} else {

		}
		user.setStatus(null);
		user.setMetaData(null);
	}
}
