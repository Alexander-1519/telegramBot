package com.rybaq.telegrambot.repository;

import com.rybaq.telegrambot.entity.Fact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactRepository extends JpaRepository<Fact, Long> {
}
