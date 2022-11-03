package com.rybaq.telegrambot.service;

import com.rybaq.telegrambot.entity.Question;
import com.rybaq.telegrambot.entity.QuestionCategory;
import com.rybaq.telegrambot.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private List<Question> questions = new ArrayList<>();

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            questions = questionRepository.findAll();
        }
        int size = questions.size();

        int randomIndex = (int) (Math.random() * size);

        Question question = questions.get(randomIndex);

        questions.remove(question);
        return question;
    }

    public Question getRandomQuestionByCategory(QuestionCategory category) {
        if (questions.isEmpty()) {
            questions = questionRepository.findByCategory(category);
        }
        int size = questions.size();

        int randomIndex = (int) (Math.random() * size);

        Question question = questions.get(randomIndex);

        questions.remove(question);
        return question;
    }
}
