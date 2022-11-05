package com.rybaq.telegrambot.service;

import com.rybaq.telegrambot.entity.Quiz;
import com.rybaq.telegrambot.entity.QuizCategory;
import com.rybaq.telegrambot.entity.QuizVariant;
import com.rybaq.telegrambot.repository.QuizRepository;
import com.rybaq.telegrambot.repository.QuizVariantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizVariantRepository quizVariantRepository;
    private List<Quiz> quizList = new ArrayList<>();
    private int quizSize = 0;

    public QuizService(QuizRepository quizRepository, QuizVariantRepository quizVariantRepository) {
        this.quizRepository = quizRepository;
        this.quizVariantRepository = quizVariantRepository;
    }

    public Quiz getRandomQuiz(QuizCategory category) {
        if(quizList.isEmpty() || !quizList.get(0).getCategory().equals(category)) {
            quizList = quizRepository.getByCategory(category);
            quizSize = quizList.size();
        }

        int size = quizList.size();

        int randomIndex = (int) (Math.random() * size);

        Quiz quiz = quizList.get(randomIndex);

        quizList.remove(quiz);
        return quiz;
    }

    public int getSizeOfQuiz() {
        return quizSize;
    }

}
