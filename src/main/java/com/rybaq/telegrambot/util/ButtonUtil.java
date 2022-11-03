package com.rybaq.telegrambot.util;

import com.rybaq.telegrambot.constant.Button;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ButtonUtil {

    public static void addButtonSkipAndDescription(SendMessage message) {
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

        message.setReplyMarkup(markup);
    }
}
