package com.telegram.bot.exceptions.exceptionHandler;

import com.telegram.bot.exceptions.BaseException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@NoArgsConstructor
@AllArgsConstructor
public class TelegramException extends BaseException {

    private String detail;

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pb.setTitle("Telegram API error.");
        pb.setDetail(detail);
        return pb;
    }
}
