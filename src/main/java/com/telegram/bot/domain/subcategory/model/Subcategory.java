package com.telegram.bot.domain.subcategory.model;

import com.telegram.bot.domain.category.model.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tb_subcategory")
@Table(name = "tb_subcategory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
