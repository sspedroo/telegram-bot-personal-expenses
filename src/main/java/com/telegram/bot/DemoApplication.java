package com.telegram.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@SpringBootApplication
@EnableAsync
public class DemoApplication {

	public static void main(String[] args) throws TelegramApiException {
		SpringApplication.run(DemoApplication.class, args);
	}

}
