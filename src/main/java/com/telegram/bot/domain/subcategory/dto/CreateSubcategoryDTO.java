package com.telegram.bot.domain.subcategory.dto;

import lombok.Data;

@Data
public class CreateSubcategoryDTO {
    private Long categoryId;
    private String name;
}
