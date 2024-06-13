package com.telegram.bot.domain.subcategory.repository;

import com.telegram.bot.domain.subcategory.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    boolean existsByName(String name);
}
