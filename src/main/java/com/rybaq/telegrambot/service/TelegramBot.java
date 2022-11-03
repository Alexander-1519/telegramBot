package com.rybaq.telegrambot.service;

import com.rybaq.telegrambot.config.BotConfig;
import com.rybaq.telegrambot.constant.Button;
import com.rybaq.telegrambot.entity.Question;
import com.rybaq.telegrambot.util.ButtonUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final QuestionService questionService;
    private Question currentQuestion = null;

    public TelegramBot(BotConfig botConfig, QuestionService questionService) {
        this.botConfig = botConfig;
        this.questionService = questionService;
        List<BotCommand> commandsList = new ArrayList<>();
//        commandsList.add(new BotCommand("/test", "it's a test"));
//        commandsList.add(new BotCommand("/description", "it's a description"));
//        commandsList.add(new BotCommand("/register", "it's a register"));
        commandsList.add(new BotCommand("/question", "get random question"));
        try {
            this.execute(new SetMyCommands(commandsList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    protected void init() {
        //инициализируйте конфигурацию здесь
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
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
        String text = "";

        if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String data = update.getCallbackQuery().getData();
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

            if (data.equals("YES BUTTON")) {
                String textToSend = "You pressed YES button.";
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setMessageId(messageId);
                editMessageText.setChatId(chatId);
                editMessageText.setText(textToSend);

                try {
                    execute(editMessageText);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (data.equals("NO BUTTON")) {
                String textToSend = "You pressed NO button.";
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setMessageId(messageId);
                editMessageText.setChatId(chatId);
                editMessageText.setText(textToSend);

                try {
                    execute(editMessageText);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if(data.equals(Button.SKIP.name())) {
                getRandomQuestion(chatId);
            } else if(data.equals(Button.DESCRIPTION.name())) {
                String answer = Arrays.stream(currentQuestion.getAnswer().split("\n"))
                        .collect(Collectors.joining("\n\n"));
                String textToSend = currentQuestion.getName() + "\n\n" + answer;
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
        }

        text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        switch (text) {
            case "/start": {
                onStartMessage(chatId, update.getMessage().getChat().getUserName());
                break;
            }
//            case "/test": {
//                sendMessage(chatId, "it's a test, bro!");
//                break;
//            }
//            case "/register": {
//                register(chatId);
//                break;
//            }
            case "/question": {
                getRandomQuestion(chatId);
                break;
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

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void register(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Do you really want to register?");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton yesButton = new InlineKeyboardButton();
        yesButton.setText("Yes");
        yesButton.setCallbackData("YES BUTTON");

        InlineKeyboardButton noButton = new InlineKeyboardButton();
        noButton.setText("No");
        noButton.setCallbackData("NO BUTTON");

        row.add(yesButton);
        row.add(noButton);

        rows.add(row);

        markup.setKeyboard(rows);

        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void getRandomQuestion(Long chatId) {
        currentQuestion = questionService.getRandomQuestion();

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentQuestion.getName());

//        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
//
//        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
//
//        List<InlineKeyboardButton> row = new ArrayList<>();
//
//        InlineKeyboardButton skipButton = new InlineKeyboardButton();
//        skipButton.setText("skip");
//        skipButton.setCallbackData(Button.SKIP.name());
//
//        InlineKeyboardButton descriptionButton = new InlineKeyboardButton();
//        descriptionButton.setText("description");
//        descriptionButton.setCallbackData(Button.DESCRIPTION.name());
//
//        row.add(skipButton);
//        row.add(descriptionButton);
//
//        rows.add(row);
//
//        markup.setKeyboard(rows);
//
//        message.setReplyMarkup(markup);

        ButtonUtil.addButtonSkipAndDescription(message);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
