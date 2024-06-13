package com.telegram.bot.domain.paymentMethod.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "tb_payment_method")
@Table(name = "tb_payment_method")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    public enum Enum {
        CREDIT_CARD(1L, "Cartão de Crédito"),
        CASH(2L, "Dinheiro"),
        PIX(3L, "Pix");

        Enum(Long id, String name){
            this.id = id;
            this.name = name;
        }

        private Long id;
        private String name;

        public PaymentMethod get(){
            return new PaymentMethod(id, name);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMethod that = (PaymentMethod) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
