package com.lawfirm;

import com.lawfirm.config.TelegramConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TelegramConfig.class)
public class LawfirmApplication {

    public static void main(String[] args) {
        SpringApplication.run(LawfirmApplication.class, args);
    }
}
