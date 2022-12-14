package com.rybaq.telegrambot.service;

import com.rybaq.telegrambot.config.BotConfig;
import com.rybaq.telegrambot.constant.Button;
import com.rybaq.telegrambot.entity.*;
import com.rybaq.telegrambot.util.ButtonUtil;
import com.rybaq.telegrambot.util.QuestionCategoryUtil;
import com.rybaq.telegrambot.util.QuizCategoryUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final QuestionService questionService;
    private final QuizService quizService;
    private final FactService factService;
    private final UserService userService;

    private Question currentQuestion = null;
    private QuestionCategory currentCategory = null;
    private QuizCategory currentQuizCategory = null;
    private Quiz currentQuizQuestion = null;
    private int quizSize = 0;
    private int currentQuestionNumber = 1;
    private final List<Integer> wrongAnswers = new ArrayList<>();

    public TelegramBot(BotConfig botConfig, QuestionService questionService, QuizService quizService,
                       FactService factService, UserService userService) {
        this.botConfig = botConfig;
        this.questionService = questionService;
        this.quizService = quizService;
        this.factService = factService;
        this.userService = userService;
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
                String questionName = update.getCallbackQuery().getMessage().getText();
                Question question = questionService.findByName(questionName);

                String answer = question.getAnswer();
                String textToSend = question.getName() + "\n\n" + answer;
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
                solveQuizAnswer(chatId, data, update);
            }
        } else {

            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            switch (text) {
                case "/start": {
                    onStartMessage(chatId,
                            update.getMessage().getChat().getUserName(),
                            update.getMessage().getChatId());
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

    private void onStartMessage(Long chatId, String username, Long userId) {
        User user = new User();
        user.setUserId(userId);
        userService.saveUser(user);

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

    private void solveQuizAnswer(Long chatId, String data, Update update) {
        boolean isAnswer = Boolean.parseBoolean(data);
        if (!isAnswer) {
            wrongAnswers.add(currentQuestionNumber - 1);
        }

        EditMessageText messageText = new EditMessageText();
        messageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        messageText.setChatId(update.getCallbackQuery().getMessage().getChatId());
        messageText.setText(currentQuestionNumber - 1 + ". " + currentQuizQuestion.getQuestion());
        ButtonUtil.addButtonsWithAnswersCompleted(messageText, currentQuizQuestion.getVariants());

        try {
            execute(messageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        if (currentQuestionNumber - 1 != quizSize) {
            getRandomQuizQuestionByCategory(chatId, currentQuizCategory);
        } else {
            if(wrongAnswers.isEmpty()){
                sendMessage(chatId, "There are no wrong answers. Great job, null!");
                sendPhoto(chatId, "src/main/resources/photo/img_2.png");
            }else {
                String wrongAnswer = "Wrong answers on questions: ";
                String collect = wrongAnswers.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(","));
                wrongAnswers.clear();
                sendMessage(chatId, wrongAnswer + collect);
                sendPhoto(chatId, "src/main/resources/photo/img_1.png");
            }
        }
    }

    private void sendPhoto(Long chatId, String photoPath) {
        SendPhoto photo = new SendPhoto();
        photo.setChatId(chatId);
        photo.setPhoto(new InputFile().setMedia(new File(photoPath)));

        try {
            execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "30 30 7 * * *")
    public void getRandomFact() {
        Fact randomFact = factService.getRandomFact();

        List<User> users = userService.getAllUsers();

        for(User user: users) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(randomFact.getName());
            sendMessage.setChatId(user.getUserId());

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Scheduled(cron = "30 30 8,20 * * *")
    public void appScheduler() {
        List<User> users = userService.getAllUsers();

        for(User user: users) {
            SendMessage message = new SendMessage();
            message.setText("????, ??????????????, ?? ???? ???????????? ??????????????? ?????????????? ???? ????????????????, ?????????? ???? ???????????? ??????????????!" +
                    " ???????? ???? ???????????? ?? ????????????????????????????, ??????-???? ???? ???????????? ?? ???????????????? ???????? ?????????????? ????????????????????????.");
            message.setChatId(user.getUserId());

            try {
                execute(message);
                sendPhoto(user.getUserId(), "src/main/resources/photo/img.png");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


}
