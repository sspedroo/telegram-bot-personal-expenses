package com.telegram.bot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class BaseException extends RuntimeException{

    //https://datatracker.ietf.org/doc/html/rfc7807
    public ProblemDetail toProblemDetail() {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pb.setTitle("Application internal server error");
        return pb;
    }
}
