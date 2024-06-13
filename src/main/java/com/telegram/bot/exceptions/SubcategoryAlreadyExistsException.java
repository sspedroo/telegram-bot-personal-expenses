package com.telegram.bot.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@NoArgsConstructor
@AllArgsConstructor
public class SubcategoryAlreadyExistsException extends BaseException{

    private String detail;
    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Subcategory already exists.");
        pb.setDetail(detail);
        return pb;
    }
}
