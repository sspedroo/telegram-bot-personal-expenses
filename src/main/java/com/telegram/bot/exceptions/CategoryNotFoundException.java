package com.telegram.bot.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@NoArgsConstructor
@AllArgsConstructor
public class CategoryNotFoundException extends BaseException{

    private Long categoryId;
    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Category not found");
        pb.setDetail("There is no category with this id: " + categoryId + ".");

        return pb;
    }
}
