package com.lawfirm;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;


@Component
public class ServerStarting {

	@Value("${server.url}")
	private String SERVER_URL;
	@Value("${telegram.url}")
	private String TELEGRAM_URL;

	private static final Logger logger = Logger.getLogger(ServerStarting.class);

	@PostConstruct
	public void getStarted() throws Exception {

		try {
			ResponseEntity<?> responseEntity = new RestTemplate().getForEntity(TELEGRAM_URL + "/setWebhook?url=" + SERVER_URL + "/admins", Object.class);
			logger.debug("Telegram`s bot webhook: " + responseEntity.getBody());

		} catch (Exception e) {
			logger.warn(e);
		}

	}


}


