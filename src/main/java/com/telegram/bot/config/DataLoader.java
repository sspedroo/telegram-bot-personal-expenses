package com.telegram.bot.config;

import com.telegram.bot.domain.category.dto.ViewCategoryDTO;
import com.telegram.bot.domain.category.service.CategoryService;
import com.telegram.bot.domain.paymentMethod.model.PaymentMethod;
import com.telegram.bot.domain.paymentMethod.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final PaymentMethodRepository paymentMethodRepository;
    private final CategoryService categoryService;


    @Override
    public void run(String... args) throws Exception {

        List<PaymentMethod> repositoryAll = paymentMethodRepository.findAll();
        if (repositoryAll.isEmpty()){
            Arrays.stream(PaymentMethod.Enum.values())
                    .forEach(paymentMethod -> paymentMethodRepository.save(paymentMethod.get()));
        }

        List<ViewCategoryDTO> allCategories = categoryService.findAllCategories();
        if (allCategories.isEmpty()){
            categoryService.createCategory("Alimentação");
            categoryService.createCategory("Casa");
            categoryService.createCategory("Dívidas");
            categoryService.createCategory("Educação");
            categoryService.createCategory("Lazer");
            categoryService.createCategory("Saúde");
            categoryService.createCategory("Pessoal");
            categoryService.createCategory("Viagem");
            categoryService.createCategory("Transporte");
        }
    }
}
