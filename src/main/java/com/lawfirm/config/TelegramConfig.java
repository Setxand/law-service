package com.lawfirm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties("telegram")
@Data
public class TelegramConfig {
	private String botUrl;
	private List<String> admins;
}
