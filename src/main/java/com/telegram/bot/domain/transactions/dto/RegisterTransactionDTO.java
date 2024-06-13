package com.telegram.bot.domain.transactions.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RegisterTransactionDTO {
    @NotBlank
    private String description;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal value;
    @NotNull
    private Long paymentMethodId;
    @NotNull
    private Long categoryId;
    private Long subcategoryId;
    private LocalDate date;
}
