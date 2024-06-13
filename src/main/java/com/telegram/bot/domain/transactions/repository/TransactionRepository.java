package com.telegram.bot.domain.transactions.repository;

import com.telegram.bot.domain.transactions.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
