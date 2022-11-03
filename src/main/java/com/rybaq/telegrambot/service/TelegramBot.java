package com.rybaq.telegrambot.service;

import com.rybaq.telegrambot.config.BotConfig;
import com.rybaq.telegrambot.constant.Button;
import com.rybaq.telegrambot.entity.Question;
import com.rybaq.telegrambot.entity.QuestionCategory;
import com.rybaq.telegrambot.util.ButtonUtil;
import com.rybaq.telegrambot.util.QuestionCategoryUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final QuestionService questionService;
    private Question currentQuestion = null;
    private QuestionCategory currentCategory = null;

    public TelegramBot(BotConfig botConfig, QuestionService questionService) {
        this.botConfig = botConfig;
        this.questionService = questionService;
        List<BotCommand> commandsList = new ArrayList<>();
        commandsList.add(new BotCommand("/start", "Start activity bot."));
        commandsList.add(new BotCommand("/categories", "Choose any category."));
        try {
            this.execute(new SetMyCommands(commandsList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String data = update.getCallbackQuery().getData();
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

            if (data.equals(Button.SKIP.name())) {
                getRandomQuestionByCategory(chatId, currentCategory);
            } else if (data.equals(Button.DESCRIPTION.name())) {
                String answer = currentQuestion.getAnswer();
                String textToSend = currentQuestion.getName() + "\n\n" + answer;
                if(!update.getCallbackQuery().getMessage().getText().equals(textToSend)) {
                    EditMessageText editMessageText = new EditMessageText();
                    editMessageText.setMessageId(messageId);
                    editMessageText.setChatId(chatId);
                    editMessageText.setText(textToSend);
                    ButtonUtil.addButtonSkipAndDescription(editMessageText);

                    try {
                        execute(editMessageText);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            } else if (QuestionCategoryUtil.categories.contains(QuestionCategory.valueOf(data))) {
                QuestionCategory category = QuestionCategory.valueOf(data);
                getRandomQuestionByCategory(chatId, category);
            }
        } else {

            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            switch (text) {
                case "/start": {
                    onStartMessage(chatId, update.getMessage().getChat().getUserName());
                    break;
                }
                case "/categories": {
                    getCategories(chatId);
                    break;
                }
                default: {
                    sendMessage(chatId, "This operation is not supported.");
                }
            }
        }
    }

    private void onStartMessage(Long chatId, String username) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Hi, " + username + ", nice to meet you!");

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Long chatId, String answer) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(answer);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

//    private void register(long chatId) {
//        SendMessage message = new SendMessage();
//        message.setChatId(chatId);
//        message.setText("Do you really want to register?");
//
//        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
//
//        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
//
//        List<InlineKeyboardButton> row = new ArrayList<>();
//
//        InlineKeyboardButton yesButton = new InlineKeyboardButton();
//        yesButton.setText("Yes");
//        yesButton.setCallbackData("YES BUTTON");
//
//        InlineKeyboardButton noButton = new InlineKeyboardButton();
//        noButton.setText("No");
//        noButton.setCallbackData("NO BUTTON");
//
//        row.add(yesButton);
//        row.add(noButton);
//
//        rows.add(row);
//
//        markup.setKeyboard(rows);
//
//        message.setReplyMarkup(markup);
//
//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }

    private void getRandomQuestion(Long chatId) {
        currentQuestion = questionService.getRandomQuestion();

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentQuestion.getName());

        ButtonUtil.addButtonSkipAndDescription(message);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void getRandomQuestionByCategory(Long chatId, QuestionCategory category) {
        currentQuestion = questionService.getRandomQuestionByCategory(category);
        currentCategory = category;

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentQuestion.getName());

        ButtonUtil.addButtonSkipAndDescription(message);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void getCategories(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Choose question's category");

        ButtonUtil.addButtonCategories(message);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
