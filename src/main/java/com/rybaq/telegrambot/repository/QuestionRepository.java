package com.rybaq.telegrambot.repository;

import com.rybaq.telegrambot.entity.Question;
import com.rybaq.telegrambot.entity.QuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByCategory(QuestionCategory category);

    Question findByName(String name);
}
