package com.telegram.bot.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@NoArgsConstructor
@AllArgsConstructor
public class SubcategoryNotFoundException extends BaseException{

    private Long subcategoryId;
    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Subcategory not found");
        pb.setDetail("There is no Subcategory with this id: " + subcategoryId + ".");

        return pb;
    }
}
