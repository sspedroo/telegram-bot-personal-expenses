package com.telegram.bot.telegram.repository;

import com.telegram.bot.telegram.domain.InscritosTelegramBot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscritosTelegramBotRepository extends JpaRepository<InscritosTelegramBot, Long> {

    boolean existsByChatId(Long chatId);
}
