package com.rybaq.telegrambot.service;

import com.rybaq.telegrambot.entity.Fact;
import com.rybaq.telegrambot.entity.Quiz;
import com.rybaq.telegrambot.repository.FactRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FactService {

    private final FactRepository factRepository;
    private List<Fact> facts = new ArrayList<>();

    public FactService(FactRepository factRepository) {
        this.factRepository = factRepository;
    }

    public Fact getRandomFact() {
        if(facts.isEmpty()) {
            facts = factRepository.findAll();
        }

        int size = facts.size();

        int randomIndex = (int) (Math.random() * size);

        Fact fact = facts.get(randomIndex);

        facts.remove(fact);
        return fact;
    }
}
