package com.telegram.bot.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodNotFoundException extends BaseException{

    private Long paymentMethodId;
    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Payment Method not found");
        pb.setDetail("There is no Payment Method with this id: " + paymentMethodId + ".");

        return pb;
    }
}
