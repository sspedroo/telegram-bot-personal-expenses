package com.telegram.bot.telegram.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_inscritos_telegram_bot")
@Table(name = "tb_inscritos_telegram_bot")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InscritosTelegramBot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
}
