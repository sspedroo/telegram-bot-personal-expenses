package com.telegram.bot.domain.transactions.service;


import com.telegram.bot.domain.category.model.Category;
import com.telegram.bot.domain.category.repository.CategoryRepository;
import com.telegram.bot.domain.paymentMethod.model.PaymentMethod;
import com.telegram.bot.domain.paymentMethod.repository.PaymentMethodRepository;
import com.telegram.bot.domain.subcategory.model.Subcategory;
import com.telegram.bot.domain.subcategory.repository.SubcategoryRepository;
import com.telegram.bot.domain.transactions.dto.RegisterTransactionDTO;
import com.telegram.bot.domain.transactions.dto.ViewTransactionDTO;
import com.telegram.bot.domain.transactions.model.Transaction;
import com.telegram.bot.domain.transactions.repository.TransactionRepository;
import com.telegram.bot.exceptions.CategoryNotFoundException;
import com.telegram.bot.exceptions.PaymentMethodNotFoundException;
import com.telegram.bot.exceptions.SubcategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final PaymentMethodRepository paymentMethodRepository;


    @Transactional
    public ViewTransactionDTO registerTransaction(RegisterTransactionDTO dto){
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId()));

        Subcategory subcategory = null;
        if (dto.getSubcategoryId() != null){
            subcategory = subcategoryRepository.findById(dto.getSubcategoryId())
                    .orElseThrow(() -> new SubcategoryNotFoundException(dto.getSubcategoryId()));
        }

        PaymentMethod paymentMethod = paymentMethodRepository.findById(dto.getPaymentMethodId())
                .orElseThrow(() -> new PaymentMethodNotFoundException(dto.getPaymentMethodId()));

        Transaction transaction = Transaction.builder()
                .description(dto.getDescription())
                .value(dto.getValue())
                .paymentMethod(paymentMethod)
                .category(category)
                .subcategory(subcategory)
                .date(dto.getDate())
                .build();

        Transaction newTransaction = transactionRepository.save(transaction);

        return new ViewTransactionDTO(newTransaction);
    }
}
