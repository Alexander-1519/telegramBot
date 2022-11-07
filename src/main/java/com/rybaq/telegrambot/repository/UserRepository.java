package com.rybaq.telegrambot.repository;

import com.rybaq.telegrambot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserId(Long id);
}
