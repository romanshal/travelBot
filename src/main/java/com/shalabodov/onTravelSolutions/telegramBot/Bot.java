package com.shalabodov.onTravelSolutions.telegramBot;

import com.shalabodov.onTravelSolutions.entity.City;
import com.shalabodov.onTravelSolutions.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private CityService cityService;

    @PostConstruct
    public void registerBot() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if(message != null && message.hasText()){
            switch (message.getText()) {
                case "/start":
                    sendMsg(message, "Приветствую!!!" + "\n" + "Введите название города,о котором хотите узнать подробнее");
                    break;
                case "/help":
                    sendMsg(message, "Чем помочь?");
                    break;
                case "Удалить":
                    sendMsg(message, "Какой город будем удалить? (Форма удаления: delГород)");
                    break;
                case "Добавить город":
                    sendMsg(message, "Какой город будем добавлять? (Форма добавления: addГород-описание)");
                    break;
                case "Изменить описание":
                    sendMsg(message, "Какой город будем изменять? (Форма изменения: updateГород-новое описание)");
                    break;
                default:
                    if(message.getText().matches("del.*")){
                        String delCity = message.getText().substring(3);
                        ;
                        if(cityService.delete(delCity) != 0){
                            sendMsg(message, "Удаленно!");
                        }
                        sendMsg(message, "Такого города нет :(");

                        break;
                    } else {
                        if(message.getText().matches("add.*")){
                            String addCity = message.getText().substring(3);
                            List <String> strings = new ArrayList();
                            for (String str : addCity.split("-", 2)) {
                                strings.add(str);
                            }
                            City city = new City();
                            city.setCity(strings.get(0));
                            city.setDescription(strings.get(1));
                            System.out.println(city);
                            cityService.save(city);
                            sendMsg(message, "Город добавлен!");
                            break;
                        } else {
                            if(message.getText().matches("update.*")){
                                String updateCity = message.getText().substring(6);
                                List <String> strings = new ArrayList();
                                for (String str : updateCity.split("-", 2)) {
                                    strings.add(str);
                                }
                                if(cityService.update(strings.get(1), strings.get(0)) != 0){
                                    sendMsg(message, "Описание изменено!");
                                } else
                                    sendMsg(message, "Такого города нет :(");
                                break;
                            }
                        }
                    }
                    Optional <City> foundCity = cityService.getByCity(message.getText());
                    if(foundCity.isPresent()){
                        sendMsg(message, foundCity.get().getDescription());
                    } else {
                        sendMsg(message, "Либо вы ввели несуществующий город, либо такого нет в базе");
                    }
                    break;
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "TravelSolutionsBot";
    }

    @Override
    public String getBotToken() {
        return "1066501856:AAHZZ1x-jc2b2EF35_SjwYAOQrk24UFUUmI";
    }

    public synchronized void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List <KeyboardRow> keyboardRowList = new ArrayList();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("Добавить город"));
        keyboardFirstRow.add(new KeyboardButton("Изменить описание"));
        keyboardFirstRow.add(new KeyboardButton("Удалить"));
        keyboardRowList.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
}
