package com.telegram.bot.domain.transactions.model;

import com.telegram.bot.domain.category.model.Category;
import com.telegram.bot.domain.paymentMethod.model.PaymentMethod;
import com.telegram.bot.domain.subcategory.model.Subcategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity(name = "tb_transaction")
@Table(name = "tb_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;
    @Column(columnDefinition = "DATE")
    private LocalDate date;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime createAt;

    @PrePersist
    private void prePersist(){
        createAt = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    }

}
