package com.telegram.bot.domain.paymentMethod.repository;

import com.telegram.bot.domain.paymentMethod.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    Optional<PaymentMethod> findByName(String name);

}
