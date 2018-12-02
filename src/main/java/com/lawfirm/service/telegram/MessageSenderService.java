package com.lawfirm.service.telegram;

import com.lawfirm.config.TelegramConfig;
import com.lawfirm.dto.telegram.*;
import com.lawfirm.dto.telegram.button.InlineKeyboardButton;
import com.lawfirm.dto.telegram.button.InlineKeyboardMarkup;
import com.lawfirm.dto.telegram.button.KeyboardButton;
import com.lawfirm.dto.telegram.button.KeyboardMarkup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


@Service
public class MessageSenderService {

    @Value("${server.url}")
    private String SERVER_URL;
    private final TelegramConfig telegramConfig;

    public MessageSenderService(TelegramConfig telegramConfig) {
        this.telegramConfig = telegramConfig;
    }

    public void sendMessage(TelegramRequest telegramRequest) {
        try {
            new RestTemplate().postForEntity(telegramConfig.getBotUrl() + "/sendMessage", telegramRequest, Void.class);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }


    public void helloMessage(Message message) {
        String messange = ResourceBundle.getBundle("dictionary").getString("HELLO_MESSAGE");
        int chatId  = message.getChat().getId();
        sendMessage(new TelegramRequest(messange,chatId));
    }


    public void simpleMessage(String message,Message m) {
        sendMessage(new TelegramRequest(message,m.getChat().getId()));
    }


    public void errorMessage(Message message) {
        String text = "men, i don`t understand this command, try again)";
        sendMessage(new TelegramRequest(text,message.getChat().getId()));
    }


    public void sendButtons(Markup markup, String text, Message message) {
        TelegramRequest telegramRequest = new TelegramRequest();
        telegramRequest.setChatId(message.getChat().getId());
        telegramRequest.setText(text);
        telegramRequest.setMarkup(markup);
        sendMessage(telegramRequest);
    }


    public void sendInlineButtons(List<List<InlineKeyboardButton>>buttons, String text, Message message) {
        Markup markup = (Markup) new InlineKeyboardMarkup(buttons);
        sendButtons(markup,text,message);
    }

    public void sendPhoto( String photo, String caption,Markup markup, Message message) {
        new RestTemplate().postForEntity(telegramConfig.getBotUrl() + "/sendPhoto",new TelegramRequest(message.getChat().getId(),markup,photo,caption),Void.class);
    }

    public void sendActions(Message message) {
        List<List<InlineKeyboardButton>>inlineKeyboardButtons = new ArrayList<>();
        InlineKeyboardButton reference = new InlineKeyboardButton();
        reference.setText("Reference");
        reference.setUrl(SERVER_URL+"/reference");

        inlineKeyboardButtons.add(new ArrayList<>(Arrays.asList(
                new InlineKeyboardButton(
                        ResourceBundle.getBundle("dictionary")
                                .getString("MENU_OF_CROISSANTS"),"MENU_DATA"))));

        inlineKeyboardButtons.add(new ArrayList<>(Arrays.asList(
                new InlineKeyboardButton(ResourceBundle.getBundle("dictionary")
                        .getString("CREATE_OWN_CROISSANT"), "CREATE_OWN_CROISSANT_DATA"))));

        inlineKeyboardButtons.add(new ArrayList<>(Arrays.asList(reference)));

        String text = ResourceBundle.getBundle("dictionary").getString("CHOOSING_ACTIONS");
        sendInlineButtons(inlineKeyboardButtons,text,message);
    }

    public void simpleQuestion(CallBackData data, String splitter,String text, Message message ) {
        List<InlineKeyboardButton>inlineKeyboardButtons = new ArrayList<>();
        String yes = ResourceBundle.getBundle("dictionary").getString("YES");
        String no = ResourceBundle.getBundle("dictionary").getString("NO");
        inlineKeyboardButtons.add(new InlineKeyboardButton(yes,data.name() + splitter + "QUESTION_YES"));
        inlineKeyboardButtons.add(new InlineKeyboardButton(no,data.name()+splitter+ "QUESTION_NO"));
        sendInlineButtons(new ArrayList<>(Arrays.asList(inlineKeyboardButtons)),text,message);
    }

    public void noEnoughPermissions(Message message) {
        String text = "You have not enough permissions to make it!";
        simpleMessage(text,message);
    }

    public void sendKeyboardButtons(Message message, List<List<KeyboardButton>> buttons, String text) {
        sendButtons((Markup) new KeyboardMarkup(buttons),text,message);
    }

    public void removeKeyboardButtons(Message message) {
        TelegramRequest telegramRequest = new TelegramRequest();
        telegramRequest.setMarkup(new ReplyKeyboardRemove(true));
        String text = ResourceBundle.getBundle("dictionary").getString("ACCEPTED");
        telegramRequest.setText(text);
        telegramRequest.setChatId(message.getChat().getId());
        sendMessage(telegramRequest);
    }
}
