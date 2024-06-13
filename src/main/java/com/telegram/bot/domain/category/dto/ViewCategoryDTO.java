package com.telegram.bot.domain.category.dto;

import com.telegram.bot.domain.category.model.Category;
import com.telegram.bot.domain.subcategory.dto.ViewSubcategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewCategoryDTO {
    private Long id;
    private String name;
    private LocalDateTime createAt;
    private List<ViewSubcategoryDTO> subcategories;

    public ViewCategoryDTO(Category entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.createAt = entity.getCreateAt();
        this.subcategories = entity.getSubcategories() == null ? null : entity.getSubcategories().stream().map(ViewSubcategoryDTO::new).toList();
    }
}
