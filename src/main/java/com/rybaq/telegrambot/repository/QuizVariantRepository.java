package com.rybaq.telegrambot.repository;

import com.rybaq.telegrambot.entity.Quiz;
import com.rybaq.telegrambot.entity.QuizVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizVariantRepository extends JpaRepository<QuizVariant, Long> {

    List<QuizVariant> findByQuiz(Quiz quiz);
}
