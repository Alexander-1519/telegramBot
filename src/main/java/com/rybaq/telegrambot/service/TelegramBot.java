package com.rybaq.telegrambot.service;

import com.rybaq.telegrambot.config.BotConfig;
import com.rybaq.telegrambot.constant.Button;
import com.rybaq.telegrambot.entity.Question;
import com.rybaq.telegrambot.entity.QuestionCategory;
import com.rybaq.telegrambot.entity.Quiz;
import com.rybaq.telegrambot.entity.QuizCategory;
import com.rybaq.telegrambot.util.ButtonUtil;
import com.rybaq.telegrambot.util.QuestionCategoryUtil;
import com.rybaq.telegrambot.util.QuizCategoryUtil;
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
import java.util.stream.Collectors;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final QuestionService questionService;
    private final QuizService quizService;
    private Question currentQuestion = null;
    private QuestionCategory currentCategory = null;
    private QuizCategory currentQuizCategory = null;
    private Quiz currentQuizQuestion = null;
    private int quizSize = 0;
    private int currentQuestionNumber = 1;
    private List<Integer> wrongAnswers = new ArrayList<>();

    public TelegramBot(BotConfig botConfig, QuestionService questionService, QuizService quizService) {
        this.botConfig = botConfig;
        this.questionService = questionService;
        this.quizService = quizService;
        List<BotCommand> commandsList = new ArrayList<>();
        commandsList.add(new BotCommand("/start", "Start activity bot."));
        commandsList.add(new BotCommand("/questions", "Choose any category in questions."));
        commandsList.add(new BotCommand("/quizzes", "Choose any category in quiz."));
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
                if (!update.getCallbackQuery().getMessage().getText().equals(textToSend)) {
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
            } else if (data.contains("QUESTION_") && QuestionCategoryUtil.categories.contains(QuestionCategory.valueOf(data))) {
                QuestionCategory category = QuestionCategory.valueOf(data);
                getRandomQuestionByCategory(chatId, category);
            } else if (data.contains("QUIZ_") && QuizCategoryUtil.categories.contains(QuizCategory.valueOf(data))) {
                QuizCategory category = QuizCategory.valueOf(data);
                currentQuestionNumber = 1;
                getRandomQuizQuestionByCategory(chatId, category);
            } else if (data.equals("false") || data.equals("true")) {
                boolean isAnswer = Boolean.parseBoolean(data);
                if (!isAnswer) {
                    wrongAnswers.add(currentQuestionNumber - 1);
                }

                if (currentQuestionNumber - 1 != quizSize) {
                    getRandomQuizQuestionByCategory(chatId, currentQuizCategory);
                } else {
                    String wrongAnswer = "Wrong answers on questions: ";
                    String collect = wrongAnswers.stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(","));
                    wrongAnswers.clear();
                    sendMessage(chatId, wrongAnswer + collect);
                }
            }
        } else {

            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            switch (text) {
                case "/start": {
                    onStartMessage(chatId, update.getMessage().getChat().getUserName());
                    break;
                }
                case "/questions": {
                    getCategories(chatId);
                    break;
                }
                case "/quizzes": {
                    getQuizCategories(chatId);
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

    private void getQuizCategories(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Choose question's category");

        ButtonUtil.addButtonQuizCategories(message);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void getRandomQuizQuestionByCategory(Long chatId, QuizCategory category) {
        currentQuizQuestion = quizService.getRandomQuiz(category);
        currentQuizCategory = category;
        quizSize = quizService.getSizeOfQuiz();

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentQuestionNumber++ + ". " + currentQuizQuestion.getQuestion());

        ButtonUtil.addButtonsWithAnswers(message, currentQuizQuestion.getVariants());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
