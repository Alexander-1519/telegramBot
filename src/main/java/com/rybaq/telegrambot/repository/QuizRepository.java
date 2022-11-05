package com.rybaq.telegrambot.repository;

import com.rybaq.telegrambot.entity.Quiz;
import com.rybaq.telegrambot.entity.QuizCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

//    @EntityGraph(value = "Quiz.variants")
    List<Quiz> getByCategory(QuizCategory category);
}
