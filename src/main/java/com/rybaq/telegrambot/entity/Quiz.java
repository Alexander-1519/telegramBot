package com.rybaq.telegrambot.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Quizes")
public class Quiz {

    @Id
    @GeneratedValue
    private Long id;

    private String question;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER)
    private List<QuizVariant> variants;

    @Enumerated(EnumType.STRING)
    private QuizCategory category;

    public QuizCategory getCategory() {
        return category;
    }

    public void setCategory(QuizCategory category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<QuizVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<QuizVariant> variants) {
        this.variants = variants;
    }
}
