package com.lawfirm.service.telegram;

import com.lawfirm.TestConstant;
import com.lawfirm.dto.telegram.Chat;
import com.lawfirm.dto.telegram.Message;
import com.lawfirm.model.lawProject.EditableComponent;
import com.lawfirm.model.lawProject.ServiceBody;
import com.lawfirm.model.lawProject.ServiceTitle;
import com.lawfirm.model.telegram.User;
import com.lawfirm.reposiory.EditableComponentRepository;
import com.lawfirm.reposiory.EditableComponents;
import com.lawfirm.reposiory.ServiceBodyRepo;
import com.lawfirm.reposiory.ServiceTitleRepo;
import com.lawfirm.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BotCommandHelperServiceTest {

	@Mock private EditableComponentRepository editableComponentRepo;
	@Mock private ServiceTitleRepo serviceTitleRepo;
	@Mock private ServiceBodyRepo serviceBodyRepo;
	@Mock private MessageSenderService senderService;
	@Mock private UserService userService;
	@Mock private BotCommandServiceHelper helper;

	@InjectMocks
	private BotCommandServiceHelper botCommandService;

	private User user;
	private Message message;

	@Test
	public void settingTitleTest() {
		createUserAndMessage("new-title-test");
		EditableComponent editableComponent = new EditableComponent(EditableComponents.TITLE, "title-test");

		when(editableComponentRepo.findByComponentKey(EditableComponents.TITLE))
																		.thenReturn(Optional.of(editableComponent));
		botCommandService.settingTitle(message, user);

		ArgumentCaptor<EditableComponent> componentArgumentCaptor = ArgumentCaptor.forClass(EditableComponent.class);
		Mockito.verify(editableComponentRepo).saveAndFlush(componentArgumentCaptor.capture());

		assertEquals(message.getText(), componentArgumentCaptor.getValue().getValue());
		assertNull(user.getStatus());
	}

	@Test
	public void newServiceImageTest() {
		createUserAndMessage("image.png");
		ServiceTitle serviceTitle = TestConstant.createTestObject(ServiceTitle.class);
		serviceTitle.setId(111L);

		when(serviceTitleRepo.findTopByOrderByIdDesc()).thenReturn(serviceTitle);
		botCommandService.newServiceImage(message, user);

		verify(serviceTitleRepo, times(1)).saveAndFlush(eq(serviceTitle));

		ArgumentCaptor<ServiceBody> captor = ArgumentCaptor.forClass(ServiceBody.class);
		verify(serviceBodyRepo).saveAndFlush(captor.capture());
		assertEquals(serviceTitle.getTitle(), captor.getValue().getTitle());
		assertEquals(serviceTitle.getId(), captor.getValue().getId());

		assertEquals(User.UserStatus.ARTICLE_CONTENT, user.getStatus());
		assertEquals(message.getText(), serviceTitle.getImage());
	}

	private void createUserAndMessage(String message) {
		this.message = new Message();
		this.message.setText(message);
		user = new User(333);
		user.setStatus(User.UserStatus.SETTING_TITLE);
		this.message.setChat(new Chat(user.getChatId()));
	}
}