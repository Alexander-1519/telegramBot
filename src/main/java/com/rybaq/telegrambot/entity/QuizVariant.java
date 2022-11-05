package com.rybaq.telegrambot.entity;

import javax.persistence.*;

@Entity
@Table(name = "Quiz_Variants")
public class QuizVariant {

    @Id
    @GeneratedValue
    private Long id;

    private String answer;

    private Boolean isAnswer;

    @ManyToOne
    private Quiz quiz;

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        isAnswer = answer;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
