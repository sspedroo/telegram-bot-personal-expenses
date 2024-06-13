package com.telegram.bot.domain.transactions.dto;

import com.telegram.bot.domain.transactions.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewTransactionDTO {
    private Long id;
    private String description;
    private BigDecimal value;
    private String paymentMethod;
    private String category;
    private String subcategory;
    private LocalDate date;
    private LocalDateTime createAt;

    public ViewTransactionDTO (Transaction entity){
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.value = entity.getValue();
        this.paymentMethod = entity.getPaymentMethod().getName();
        this.category = entity.getCategory().getName();
        this.subcategory = entity.getSubcategory() == null ? null : entity.getSubcategory().getName();
        this.date = entity.getDate();
        this.createAt = entity.getCreateAt();
    }
}
