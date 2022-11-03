package com.rybaq.telegrambot.service;

import com.rybaq.telegrambot.entity.Question;
import com.rybaq.telegrambot.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question getRandomQuestion() {
        List<Question> questions = questionRepository.findAll();
        int size = questions.size();

        int randomIndex = (int) (Math.random() * size);
        return questions.get(randomIndex);
    }
}
