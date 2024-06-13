package com.telegram.bot.domain.subcategory.service;

import com.telegram.bot.domain.category.model.Category;
import com.telegram.bot.domain.category.repository.CategoryRepository;
import com.telegram.bot.domain.subcategory.dto.CreateSubcategoryDTO;
import com.telegram.bot.domain.subcategory.dto.ViewSubcategoryDTO;
import com.telegram.bot.domain.subcategory.model.Subcategory;
import com.telegram.bot.domain.subcategory.repository.SubcategoryRepository;
import com.telegram.bot.exceptions.CategoryAlreadyExistsException;
import com.telegram.bot.exceptions.CategoryNotFoundException;
import com.telegram.bot.exceptions.SubcategoryAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ViewSubcategoryDTO createSubcategory(CreateSubcategoryDTO dto){
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId()));

        if (subcategoryRepository.existsByName(dto.getName())){
            throw new SubcategoryAlreadyExistsException("Already exists subcategory with this name.");
        }

        Subcategory subcategory = Subcategory.builder()
                .name(dto.getName())
                .category(category)
                .build();

        Subcategory newSubcategory = subcategoryRepository.save(subcategory);

        category.addSubcategory(newSubcategory);
        categoryRepository.save(category);

        return new ViewSubcategoryDTO(newSubcategory);
    }
}
