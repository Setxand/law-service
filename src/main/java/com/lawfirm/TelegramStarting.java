package com.lawfirm;


import com.lawfirm.config.TelegramConfig;
import com.lawfirm.model.telegram.User;
import com.lawfirm.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;


@Component
public class TelegramStarting {

	@Value("${server.url}")
	private String SERVER_URL;
	private final UserService userService;
	private final TelegramConfig telegramConfig;

	private static final Logger logger = Logger.getLogger(TelegramStarting.class);

	@Autowired
	public TelegramStarting(UserService userService, TelegramConfig telegramConfig) {
		this.userService = userService;
		this.telegramConfig = telegramConfig;
	}

	@PostConstruct
	public void getStarted() {

		try {
			ResponseEntity<?> responseEntity = new RestTemplate().getForEntity( telegramConfig.getBotUrl()+ "/setWebhook?url=" + SERVER_URL + "/admins", Object.class);
			logger.debug("Telegram`s bot webhook: " + responseEntity.getBody());

		} catch (Exception e) {
			logger.warn(e);
		}

	}


	@PostConstruct
	public void createAdmins() {
		for (String admin : telegramConfig.getAdmins()) {
			User user = userService.getOrNew(new Integer(admin));

			if (user.getChatId() == null) {
				user = new User(new Integer(admin));
				userService.save(user);
			}
		}

	}
}


