package com.lawfirm.exception;

import com.lawfirm.dto.telegram.TelegramRequest;
import com.lawfirm.service.telegram.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired MessageSenderService senderService;

    @ExceptionHandler(BotException.class)
    public void internalError(final BotException e) {
        senderService.sendMessage(new TelegramRequest(e.getMessage(), e.getChatId()));
    }
}
