package com.rybaq.telegrambot.util;

import com.rybaq.telegrambot.constant.Button;
import com.rybaq.telegrambot.entity.QuestionCategory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class ButtonUtil {

    public static void addButtonSkipAndDescription(SendMessage message) {
        message.setReplyMarkup(getMarkup());
    }

    public static void addButtonSkipAndDescription(EditMessageText message) {
        message.setReplyMarkup(getMarkup());
    }

    public static void addButtonCategories(SendMessage message) {
        message.setReplyMarkup(getMarkup(QuestionCategoryUtil.categories));
    }

    private static InlineKeyboardMarkup getMarkup() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton skipButton = new InlineKeyboardButton();
        skipButton.setText("skip");
        skipButton.setCallbackData(Button.SKIP.name());

        InlineKeyboardButton descriptionButton = new InlineKeyboardButton();
        descriptionButton.setText("description");
        descriptionButton.setCallbackData(Button.DESCRIPTION.name());

        row.add(skipButton);
        row.add(descriptionButton);

        rows.add(row);

        markup.setKeyboard(rows);

        return markup;
    }

    private static InlineKeyboardMarkup getMarkup(List<QuestionCategory> categories) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for(QuestionCategory category: categories) {
            List<InlineKeyboardButton> row = new ArrayList<>();

            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(category.name());
            button.setCallbackData(category.name());
            row.add(button);

            rows.add(row);
        }

        markup.setKeyboard(rows);

        return markup;
    }

    private void sendMessage(Long chatId, String answer) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(answer);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("weather");
        row.add("get random joke");

        keyboardRows.add(row);

        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add("user list");
        secondRow.add("get random info");
        secondRow.add("check my answers");

        keyboardRows.add(secondRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }

}
