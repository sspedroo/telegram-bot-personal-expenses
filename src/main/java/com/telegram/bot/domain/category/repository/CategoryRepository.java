package com.telegram.bot.domain.category.repository;

import com.telegram.bot.domain.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
    boolean existsByName(String name);
}
