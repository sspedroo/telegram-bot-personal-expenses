package com.telegram.bot.domain.subcategory.dto;

import com.telegram.bot.domain.subcategory.model.Subcategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewSubcategoryDTO {
    private Long id;
    private String name;

    public ViewSubcategoryDTO(Subcategory subcategory){
        this.id = subcategory.getId();
        this.name = subcategory.getName();
    }
}
