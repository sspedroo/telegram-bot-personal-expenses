package com.telegram.bot.domain.category.service;

import com.telegram.bot.domain.category.dto.ViewCategoryDTO;
import com.telegram.bot.domain.category.model.Category;
import com.telegram.bot.domain.category.repository.CategoryRepository;
import com.telegram.bot.exceptions.CategoryAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public ViewCategoryDTO createCategory(String name){
        if (categoryRepository.existsByName(name)){
            throw new CategoryAlreadyExistsException("Already exists category with this name.");
        }

        Category category = Category.builder()
                .name(name)
                .build();
        categoryRepository.save(category);

        return new ViewCategoryDTO(category);
    }

    @Transactional(readOnly = true)
    public List<ViewCategoryDTO> findAllCategories(){
        List<Category> all = categoryRepository.findAll();
        return all.stream().map(ViewCategoryDTO::new).toList();
    }
}
