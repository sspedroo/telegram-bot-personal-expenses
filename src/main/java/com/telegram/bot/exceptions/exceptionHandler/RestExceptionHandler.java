package com.telegram.bot.exceptions.exceptionHandler;

import com.telegram.bot.exceptions.BaseException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ProblemDetail handleBaseException(BaseException e) {
        return e.toProblemDetail();
    }
}
